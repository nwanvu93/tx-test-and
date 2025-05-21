package com.nwanvu.txe.ui.common

import androidx.lifecycle.ViewModel
import com.nwanvu.txe.data.network.Failure
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {

    protected val failure: MutableStateFlow<Failure?> = MutableStateFlow(null)
    protected val isLoading = MutableStateFlow(false)

    protected open fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}