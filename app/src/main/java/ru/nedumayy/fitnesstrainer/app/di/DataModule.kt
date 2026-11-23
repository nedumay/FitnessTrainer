package ru.nedumayy.fitnesstrainer.app.di

import android.app.Application
import android.content.Context
import ru.nedumayy.fitnesstrainer.app.data.database.AppDataBase
import ru.nedumayy.fitnesstrainer.app.data.database.NotificationDao
import ru.nedumayy.fitnesstrainer.app.data.network.ApiFactory
import ru.nedumayy.fitnesstrainer.app.data.network.ApiService
import ru.nedumayy.fitnesstrainer.app.data.repository.RepositoryApiImpl
import ru.nedumayy.fitnesstrainer.app.data.repository.RepositoryFirebaseImpl
import ru.nedumayy.fitnesstrainer.app.data.repository.RepositoryNotificationImpl
import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryApi
import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryNotification
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