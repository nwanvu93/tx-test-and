package com.nwanvu.txe.ui.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nwanvu.txe.R
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.ui.common.UiState
import com.nwanvu.txe.ui.views.AppBar
import com.nwanvu.txe.ui.views.EmptyView
import com.nwanvu.txe.ui.views.LoadingView
import com.nwanvu.txe.ui.views.UserView
import com.nwanvu.txe.utils.LoadMoreListHandler

/**
 * Composable function for displaying the user list screen.
 *
 * @param viewModel [UserListViewModel] has initialized by Hilt
 * @param navigateToDetails Callback function to navigate to user details screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel<UserListViewModel>(),
    navigateToDetails: (username: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = { AppBar(stringResource(R.string.app_name)) },
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isLoading, onRefresh = viewModel::refresh
        ) {
            AnimatedContent(uiState.isLoading) { loading ->
                // return views based on the UI state
                if (loading) {
                    LoadingView(Modifier.fillMaxSize())
                } else {
                    if (uiState.failure != null || uiState.data.isEmpty()) {
                        EmptyView(Modifier.fillMaxSize())
                    } else {
                        // create list state to handle load more
                        val listState = rememberLazyListState()
                        ContentView(listState, innerPadding, uiState, navigateToDetails)
                        LoadMoreListHandler(listState = listState) {
                            viewModel.loadMore()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentView(
    listState: LazyListState,
    padding: PaddingValues,
    state: UiState<List<User>>,
    itemClick: (String) -> Unit
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            start = padding.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
            end = padding.calculateStartPadding(LayoutDirection.Rtl) + 16.dp,
            top = padding.calculateTopPadding() + 16.dp,
            bottom = padding.calculateBottomPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val list = state.data
        items(count = list.size) {
            UserView(list[it], onClick = itemClick)
            if (it == list.size - 1 && state.canLoadMore) {
                LoadingView(
                    modifier = Modifier
                        .padding(16.dp)
                        .requiredHeight(32.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}