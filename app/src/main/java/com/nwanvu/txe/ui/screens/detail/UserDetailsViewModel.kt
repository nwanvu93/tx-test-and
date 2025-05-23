package com.nwanvu.txe.ui.screens.detail

import androidx.lifecycle.viewModelScope
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.domain.interactor.GetUserDetailUseCase
import com.nwanvu.txe.ui.common.BaseViewModel
import com.nwanvu.txe.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for handling user details screen.
 */
@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailUC: GetUserDetailUseCase,
) : BaseViewModel() {

    private val user: MutableStateFlow<User?> = MutableStateFlow(null)

    /**
     * UI state exposed to the UI.
     */
    val uiState: StateFlow<UiState<out User?>> = combine(
        user, isLoading, failure
    ) { user, isLoading, failure ->
        when {
            isLoading -> {
                UiState(data = null, isLoading = true)
            }

            failure != null -> {
                UiState(data = null, failure = failure)
            }

            else -> {
                UiState(data = user)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState(data = null, isLoading = true)
    )

    /**
     * Executes the GetUserDetailUseCase to fetch user details from the API.
     *
     * @param username Username of the user to fetch details for.
     */
    fun fetchDetails(username: String) {
        isLoading.value = true
        getUserDetailUC(GetUserDetailUseCase.Params(username), viewModelScope) {
            isLoading.value = false
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    /**
     * Handles a successful user details response by updating the user details.
     *
     * @param user User details returned from the API.
     */
    private fun handleSuccess(user: User) {
        this.user.value = user
    }
}