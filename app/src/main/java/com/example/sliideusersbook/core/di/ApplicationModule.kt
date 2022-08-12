package com.example.sliideusersbook.core.di

import com.example.sliideusersbook.common.di.UsersModule
import com.example.sliideusersbook.core.rx.AppSchedulersProvider
import com.example.sliideusersbook.core.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        NetworkModule::class,
        UsersModule::class
    ]
)

class ApplicationModule {
    @Provides
    @Singleton
    fun schedulers(): SchedulersProvider = AppSchedulersProvider()
}