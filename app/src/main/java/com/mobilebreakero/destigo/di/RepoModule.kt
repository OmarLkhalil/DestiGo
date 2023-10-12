package com.mobilebreakero.destigo.di

import com.mobilebreakero.data.apiservices.ApiServices
import com.mobilebreakero.data.repoimpl.SearchResultRepoImpl
import com.mobilebreakero.domain.repo.SearchResultRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideSearchResultRepo(apiServices: ApiServices): SearchResultRepo {
        return SearchResultRepoImpl(apiServices = apiServices)
    }
}