package com.nwanvu.txe.ui.screens.detail

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent

@Module
@InstallIn(ViewComponent::class)
class UserDetailsModule {

    /**
     * Provides the [UserDetailsViewModel] instance.
     */
    @Provides
    fun provideUserDetailsViewModel(viewModel: UserDetailsViewModel): ViewModel {
        return viewModel
    }
}