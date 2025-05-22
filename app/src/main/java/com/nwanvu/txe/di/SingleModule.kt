package com.nwanvu.txe.di

import com.nwanvu.txe.data.repository.UsersDataRepository
import com.nwanvu.txe.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is installed in the [SingletonComponent], ensuring that the provided instances are singletons
 */
@Module
@InstallIn(SingletonComponent::class)
class SingleModule {
    /**
     * Provides the [UsersRepository] instance.
     */
    @Provides
    fun provideUsersRepository(repository: UsersDataRepository): UsersRepository {
        return repository
    }
}