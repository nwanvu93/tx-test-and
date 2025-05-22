package com.nwanvu.txe.ui.screens.list

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent

@Module
@InstallIn(ViewComponent::class)
class UserListModule {

    /**
     * Provides the [UserListViewModel] instance.
     */
    @Provides
    fun provideListViewModel(viewModel: UserListViewModel): ViewModel {
        return viewModel
    }
}