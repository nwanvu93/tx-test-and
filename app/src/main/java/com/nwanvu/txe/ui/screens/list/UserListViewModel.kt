package com.nwanvu.txe.ui.screens.list

import androidx.lifecycle.viewModelScope
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.domain.interactor.GetUserListUseCase
import com.nwanvu.txe.ui.common.BaseViewModel
import com.nwanvu.txe.ui.common.UiState
import com.nwanvu.txe.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for handling user list screen.
 */
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUC: GetUserListUseCase,
) : BaseViewModel() {

    companion object {
        private const val START_PAGE = 0
    }

    /**
     * Fetch users on initialization.
     */
    init {
        fetchUsers()
    }

    private val users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    private var currentPage: Int = START_PAGE
    private var canLoadMore: Boolean = false

    /**
     * UI state exposed to the UI.
     */
    val uiState: StateFlow<UiState<List<User>>> = combine(
        users, isLoading, failure
    ) { users, isLoading, failure ->
        when {
            isLoading -> {
                UiState(data = emptyList(), isLoading = true)
            }

            failure != null -> {
                UiState(data = emptyList(), failure = failure)
            }

            else -> {
                UiState(data = users, canLoadMore = canLoadMore)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState(data = emptyList(), isLoading = true)
    )

    /**
     * Executes the GetUserListUseCase to fetch users from the API.
     *
     * Calculates the next 'since' parameter based on the current page and page size.
     */
    private fun fetchUsers() {
        isLoading.value = currentPage == START_PAGE
        val nextSince = currentPage * Constants.API_PAGE_SIZE
        val params = GetUserListUseCase.Params(Constants.API_PAGE_SIZE, nextSince)
        getUserListUC(params, viewModelScope) {
            isLoading.value = false
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    /**
     * Refreshes the user list by resetting the current page to the [START_PAGE]
     */
    fun refresh() {
        currentPage = START_PAGE
        fetchUsers()
    }

    /**
     * Loads the next page of users by incrementing the current page
     */
    fun loadMore() {
        currentPage++
        fetchUsers()
    }

    /**
     * Handles a successful user list response by updating the users list
     *
     * @param users List of users returned from the API.
     */
    private fun handleSuccess(users: List<User>) {
        canLoadMore = users.size == Constants.API_PAGE_SIZE
        if (currentPage == START_PAGE) {
            this.users.value = users
        } else {
            this.users.value += users
        }
    }
}