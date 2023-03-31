package com.example.testproject.app.di

import com.example.testproject.app.data.repository.RepositoryImpl
import com.example.testproject.app.domain.repository.Repository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepositoryImpl(impl: RepositoryImpl): Repository
}