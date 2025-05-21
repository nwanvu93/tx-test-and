package com.nwanvu.txe.ui.screens.list

import androidx.lifecycle.ViewModel
import com.nwanvu.txe.data.repository.UsersDataRepository
import com.nwanvu.txe.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserListModule {
    @Provides
    fun provideUsersRepository(repository: UsersDataRepository): UsersRepository {
        return repository
    }

    @Provides
    fun provideListViewModel(viewModel: ListViewModel): ViewModel {
        return viewModel
    }
}