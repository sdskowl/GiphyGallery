package com.example.giphygallery.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.data.network.GiphyService
import com.example.data.repository.GiphyRepoImpl
import com.example.data.storage.room.AppDb
import com.example.data.storage.sh_prefs.SPrefStorageImpl
import com.example.data.storage.sh_prefs.SPrefStorageInterface
import com.example.domain.repository.GiphyRepo
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .client(client)
        .baseUrl("https://api.giphy.com/v1/gifs/")
        .build()

    @Provides
    @Singleton
    fun provideIpCheckService(retrofit: Retrofit): GiphyService =
        retrofit.create(GiphyService::class.java)

    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext context: Context): AppDb = Room.databaseBuilder(
        context,
        AppDb::class.java,
        "giphy_db"
    ).build()

    @Provides
    @Singleton
    fun provideGiphyRepo(
        giphyService: GiphyService,
        sPrefStorage: SPrefStorageInterface,
        appDb: AppDb
    ): GiphyRepo = GiphyRepoImpl(
        giphyService, sPrefStorage, appDb
    )

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSPrefStorage(settings: SharedPreferences): SPrefStorageInterface =
        SPrefStorageImpl(settings = settings)
}