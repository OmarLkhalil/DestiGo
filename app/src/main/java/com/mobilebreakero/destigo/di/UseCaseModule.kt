package com.mobilebreakero.destigo.di

import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSearchResultUseCase(searchResultRepo: SearchResultRepo): SearchResultUseCase {
        return SearchResultUseCase(searchResultRepo)
    }
}