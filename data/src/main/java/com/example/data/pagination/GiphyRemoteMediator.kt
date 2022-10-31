package com.example.data.pagination

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.network.GiphyService
import com.example.data.network.models.GiphyData
import com.example.data.storage.room.AppDb
import com.example.data.storage.room.GiphyDao
import com.example.data.storage.room.RemoteKeysDao
import com.example.data.storage.room.models.Gif
import com.example.data.storage.room.models.RemoteKeys
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.invoke
import java.io.IOException

private const val TAG = "RemoteMediator"

@OptIn(ExperimentalPagingApi::class)
class GiphyRemoteMediator(
    private var startPage: Int = 0,
    private val query: String,
    private val service: GiphyService,
    private val giphyDao: GiphyDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val appDb: AppDb
) : RemoteMediator<Int, Gif>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Gif>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d(TAG, "load type = REFRESH $remoteKeys")
                remoteKeys?.nextKey?.minus(1) ?: startPage
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                Log.d(TAG, "load type = PREPEND prev key = $prevKey")
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                Log.d(TAG, "load type = APPEND, remote keys = $remoteKeys")
                nextKey
            }
        }


        val apiQuery = query
        val apiResponse =
            service.searchGifs(
                q = apiQuery,
                offset = page * state.config.pageSize,
                limit = state.config.pageSize
            )
        val giphyData: List<GiphyData.Data?>? = apiResponse.invoke()?.data

        if (giphyData != null) {
            val endOfPaginationReached = giphyData.isEmpty()
            appDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    giphyDao.clearGifs()
                }
                val prevKey = if (page == startPage) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d(TAG, "prevKey = $prevKey, nextKey = $nextKey page = $page")
                val keys = giphyData.map {
                    RemoteKeys(
                        gifId = it?.id!!,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                val dataDb =
                    giphyData.map {
                        Gif(
                            id = it?.id!!.trim(),
                            originalUrl = it.images?.original?.url!!.trim(),
                            smallUrl = it.images.fixedWidth?.url!!.trim(),
                            searchWords = query,
                            hide = giphyDao.findHidden(it.id) != null
                        )
                    }
                remoteKeysDao.insertAll(keys)
                giphyDao.insertAll(dataDb)
            }



            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } else return MediatorResult.Error(fail("Something wrong"))

    }

    private fun fail(message: String): Throwable {
        return IOException(message)
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Gif>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { gif ->
            appDb.withTransaction { remoteKeysDao.remoteKeysGifId(gif.id) }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Gif>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { gif ->
                // Get the remote keys of the first items retrieved
                appDb.withTransaction { remoteKeysDao.remoteKeysGifId(gif.id) }
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Gif>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { gif ->
                appDb.withTransaction { remoteKeysDao.remoteKeysGifId(gif.id) }
            }
        }
    }

}