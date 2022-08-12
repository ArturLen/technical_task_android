package com.example.sliideusersbook.common.di

import com.example.sliideusersbook.common.repository.RemoteUsersRepositoryImpl
import com.example.sliideusersbook.common.repository.UsersRepository
import com.example.sliideusersbook.common.usecase.UsersUseCase
import com.example.sliideusersbook.common.usecase.UsersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UsersModule {
    @Binds
    fun providesUsersUseCase(useCase: UsersUseCaseImpl): UsersUseCase

    @Binds
    fun providesUsersRepository(repository: RemoteUsersRepositoryImpl): UsersRepository
}