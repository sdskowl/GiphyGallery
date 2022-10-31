package com.example.giphygallery.di

import com.example.domain.repository.GiphyRepo
import com.example.domain.usecase.GetListGifsUseCase
import com.example.domain.usecase.GetScrollStateUseCase
import com.example.domain.usecase.HideGifUseCase
import com.example.domain.usecase.StartActionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideGetListGifsUseCase(giphyRepo: GiphyRepo): GetListGifsUseCase =
        GetListGifsUseCase(giphyRepo = giphyRepo)

    @Provides
    fun provideStartActionUseCase(giphyRepo: GiphyRepo): StartActionUseCase =
        StartActionUseCase(giphyRepo = giphyRepo)

    @Provides
    fun provideGetScrollStateUseCase(giphyRepo: GiphyRepo): GetScrollStateUseCase =
        GetScrollStateUseCase(giphyRepo = giphyRepo)

    @Provides
    fun provideHideGifUseCase(giphyRepo: GiphyRepo): HideGifUseCase =
        HideGifUseCase(giphyRepo = giphyRepo)
}