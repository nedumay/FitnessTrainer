package com.example.testproject.app.di

import com.example.testproject.app.data.network.ApiFactory
import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.repository.RepositoryApiImpl
import com.example.testproject.app.data.repository.RepositoryFirebaseImpl
import com.example.testproject.app.domain.repository.RepositoryApi
import com.example.testproject.app.domain.repository.RepositoryFirebase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepositoryImpl(impl: RepositoryFirebaseImpl): RepositoryFirebase

    @Binds
    @ApplicationScope
    fun bindRepositoryApiImpl(impl: RepositoryApiImpl): RepositoryApi

    companion object{
        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}