package com.example.testproject.app.di

import android.app.Application
import android.content.Context
import com.example.testproject.app.data.database.AppDataBase
import com.example.testproject.app.data.database.NotificationDao
import com.example.testproject.app.data.network.ApiFactory
import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.repository.RepositoryApiImpl
import com.example.testproject.app.data.repository.RepositoryFirebaseImpl
import com.example.testproject.app.data.repository.RepositoryNotificationImpl
import com.example.testproject.app.domain.repository.RepositoryApi
import com.example.testproject.app.domain.repository.RepositoryFirebase
import com.example.testproject.app.domain.repository.RepositoryNotification
import dagger.Binds
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepositoryImpl(impl: RepositoryFirebaseImpl): RepositoryFirebase

    @Binds
    @ApplicationScope
    fun bindRepositoryApiImpl(impl: RepositoryApiImpl): RepositoryApi

    @Binds
    @ApplicationScope
    fun bindRepositoryNotificationImpl(impl: RepositoryNotificationImpl): RepositoryNotification

    companion object {

        @Provides
        @ApplicationScope
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }
//        @Provides
//        @ApplicationScope
//        fun provideApiService(): ApiService {
//            return ApiFactory.apiService
//        }

        @Provides
        @ApplicationScope
        fun provideNotificationDao(
            application: Application
        ) : NotificationDao {
            return AppDataBase.getInstance(application).notificationDao()
        }
    }
}