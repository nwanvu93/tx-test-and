package com.nwanvu.txe.ui.screens.list

import androidx.lifecycle.viewModelScope
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.data.network.Failure
import com.nwanvu.txe.domain.interactor.GetUserListUseCase
import com.nwanvu.txe.ui.common.BaseViewModel
import com.nwanvu.txe.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * UiState for the user list screen.
 */
data class UiState(
    val items: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val failure: Failure? = null
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getUserListUC: GetUserListUseCase,
) : BaseViewModel() {

    companion object {
        private const val START_PAGE = 0
    }

    init {
        fetchUsers()
    }

    private val users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    private var currentPage: Int = START_PAGE

    val uiState: StateFlow<UiState> = combine(
        users, isLoading, failure
    ) { users, isLoading, failure ->
        when {
            isLoading -> {
                UiState(isLoading = true)
            }

            failure != null -> {
                UiState(failure = failure)
            }

            else -> {
                UiState(items = users)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState(isLoading = true)
    )

    private fun fetchUsers() {
        isLoading.value = currentPage == START_PAGE
        val nextSince = currentPage * Constants.API_PAGE_SIZE
        val params = GetUserListUseCase.Params(Constants.API_PAGE_SIZE, nextSince)
        getUserListUC(params, viewModelScope) {
            isLoading.value = false
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    fun refresh() {
        currentPage = START_PAGE
        fetchUsers()
    }

    fun loadMore() {
        currentPage++
        fetchUsers()
    }

    private fun handleSuccess(users: List<User>) {
        if (currentPage == START_PAGE) {
            this.users.value = users
        } else {
            this.users.value += users
        }
    }
}