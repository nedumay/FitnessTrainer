package com.example.testproject.app.di

import com.example.testproject.app.data.repository.RepositoryFirebaseImpl
import com.example.testproject.app.domain.repository.RepositoryFirebase
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepositoryImpl(impl: RepositoryFirebaseImpl): RepositoryFirebase
}