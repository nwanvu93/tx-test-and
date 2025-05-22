package com.nwanvu.txe.ui.common

import com.nwanvu.txe.data.network.Failure

/**
 * UiState for handling UI states.
 */
data class UiState<T>(
    val data: T,
    val isLoading: Boolean = false,
    val canLoadMore: Boolean = false,
    val failure: Failure? = null
)
