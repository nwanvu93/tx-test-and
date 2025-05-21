package com.nwanvu.txe.ui.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.nwanvu.txe.R
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.ui.views.AppBar
import com.nwanvu.txe.ui.views.EmptyView
import com.nwanvu.txe.ui.views.LoadingView
import com.nwanvu.txe.utils.LoadMoreListHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: ListViewModel = hiltViewModel<ListViewModel>(),
    navigateToDetails: (username: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = { AppBar(stringResource(R.string.app_name)) },
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isLoading,
            onRefresh = viewModel::refresh
        ) {
            AnimatedContent(uiState.isLoading) { loading ->
                if (loading) {
                    LoadingView(Modifier.fillMaxSize())
                } else {
                    if (uiState.failure != null || uiState.items.isEmpty()) {
                        EmptyView(Modifier.fillMaxSize())
                    } else {
                        val listState = rememberLazyListState()
                        LazyColumn(
                            state = listState,
                            contentPadding = PaddingValues(
                                start = innerPadding.calculateStartPadding(
                                    LayoutDirection.Ltr
                                ) + 16.dp,
                                end = innerPadding.calculateStartPadding(
                                    LayoutDirection.Rtl
                                ) + 16.dp,
                                top = innerPadding.calculateTopPadding() + 16.dp,
                                bottom = innerPadding.calculateBottomPadding() + 16.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp),

                            ) {
                            val list = uiState.items
                            items(count = list.size) {
                                UserView(list[it], onClick = navigateToDetails)
                            }
                        }

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
fun UserView(user: User, onClick: (username: String) -> Unit) {
    Card(
        modifier = Modifier
            .requiredHeight(150.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = { onClick(user.username ?: return@Card) }) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = user.username,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .requiredWidth(100.dp)
                    .aspectRatio(1f)
                    .background(Color.LightGray),
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = user.username.toString(), style = MaterialTheme.typography.titleLarge)
                HorizontalDivider()
                Text(text = user.profileUrl.toString())
            }
        }
    }
}