package com.example.data.repository

import android.util.Log
import androidx.paging.*
import com.example.data.network.GiphyService
import com.example.data.pagination.GiphyRemoteMediator
import com.example.data.storage.room.AppDb
import com.example.data.storage.room.models.Gif
import com.example.data.storage.room.models.GifHide
import com.example.data.storage.sh_prefs.SPrefStorageInterface
import com.example.domain.models.GifDomain
import com.example.domain.models.UiAction
import com.example.domain.models.UiModel
import com.example.domain.models.UiState
import com.example.domain.repository.GiphyRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class GiphyRepoImpl(
    private val giphyService: GiphyService,
    private val sPrefStorage: SPrefStorageInterface,
    private val appDb: AppDb
) : GiphyRepo {
    private val TAG: String = "GiphyRepoImpl"
    private val initialQuery: String = sPrefStorage.getQuerySearch()
    private val lastQueryScrolled: String = sPrefStorage.getLastQuerySearch()
    private val actionStateFlow = MutableSharedFlow<UiAction>()

    //here we make new query
    private val searches: Flow<UiAction.Search> = actionStateFlow
        .filterIsInstance<UiAction.Search>()
        .distinctUntilChanged()
        .onStart { emit(UiAction.Search(query = initialQuery)) }

    //just for scrolling up
    private val queriesScrolled = actionStateFlow
        .filterIsInstance<UiAction.Scroll>()
        .distinctUntilChanged()
        .onStart { emit(UiAction.Scroll(currentQuery = lastQueryScrolled)) }

    /**
     * This fun we use only here to make new query and get flow. It contains paging config and etc
     * */
    override fun searchGifs(query: String): Flow<PagingData<GifDomain>> {
        Log.d(TAG, "new query $query")
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { appDb.giphyDao().gifBySearchHistory(dbQuery) }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = true),
            remoteMediator = GiphyRemoteMediator(
                query = query,
                service = giphyService,
                appDb = appDb
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { paginData ->
            paginData.map {
                GifDomain(
                    id = it.id,
                    originalUrl = it.originalUrl,
                    smallUrl = it.smallUrl,
                    searchWords = it.searchWords,
                    hide = it.hide
                )
            }
        }
    }

    /**
     * Paging flow here. New string for query we take from searches flow
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getListGifs(): Flow<PagingData<UiModel>> = searches.flatMapLatest { search ->
        searchGifs(query = search.query).map { pagingData ->
            pagingData.map { item ->
                UiModel.RepoItem(item)
            }
        }
    }

    /**
     * Here we only send action to query new search.
     * */
    override suspend fun startAction(action: UiAction) {
        actionStateFlow.emit(action)
    }

    /**
     * Here we save current and last search. We use this part to scroll up when we make new query.
     * */
    override fun getScrollState(): Flow<UiState> {
        return combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            sPrefStorage.setQuerySearch(search.query)
            sPrefStorage.setLastQuerySearch(scroll.currentQuery)
            UiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                // If the search query matches the scroll query, the user has scrolled
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
    }

    /**
     * To hide from recycler we change here property hide from model in DB and also add to hiding list in DB
     * */
    override suspend fun hideGif(gif: GifDomain) {
        appDb.giphyDao().addGifToHideList(
            gif = GifHide(id = gif.id)
        )
        appDb.giphyDao().hideGifInCurrentData(gif = gif.mapToData().apply { hide = true })
    }

    private fun GifDomain.mapToData(): Gif = Gif(
        id = id,
        originalUrl = originalUrl,
        smallUrl = smallUrl,
        searchWords = searchWords,
        hide = hide
    )
}