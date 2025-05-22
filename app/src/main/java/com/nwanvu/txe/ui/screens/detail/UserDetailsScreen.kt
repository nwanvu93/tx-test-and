package com.nwanvu.txe.ui.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nwanvu.txe.R
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.ui.screens.list.UserListViewModel
import com.nwanvu.txe.ui.views.AppBar
import com.nwanvu.txe.ui.views.EmptyView
import com.nwanvu.txe.ui.views.LoadingView
import com.nwanvu.txe.ui.views.UserView

/**
 * Composable function for displaying the user details screen.
 *
 * @param username Username of the user to display details for
 * @param viewModel [UserDetailsViewModel] has initialized by Hilt
 * @param navigateBack Callback function to navigate back to the previous screen
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailsScreen(
    username: String,
    viewModel: UserDetailsViewModel = hiltViewModel<UserDetailsViewModel>(),
    navigateBack: () -> Unit,
) {

    viewModel.fetchDetails(username)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar(stringResource(R.string.title_details), onBackClick = navigateBack) },
    ) { innerPadding ->
        AnimatedContent(
            uiState.isLoading, modifier = Modifier.padding(
                PaddingValues(
                    start = innerPadding.calculateStartPadding(
                        LayoutDirection.Ltr
                    ) + 16.dp,
                    end = innerPadding.calculateStartPadding(
                        LayoutDirection.Rtl
                    ) + 16.dp,
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp
                ),
            )
        ) { loading ->
            // return views based on the UI state
            if (loading) {
                LoadingView(Modifier.fillMaxSize())
            } else {
                if (uiState.data != null) {
                    ContentView(uiState.data!!)
                } else {
                    EmptyView(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
private fun ContentView(data: User) {
    Column {
        UserView(data, onClick = {})
        Spacer(modifier = Modifier.requiredHeight(20.dp))
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FollowView(
                Modifier.weight(1f),
                R.drawable.group_24px,
                R.string.followers,
                data.followers.toString()
            )
            FollowView(
                Modifier.weight(1f),
                R.drawable.kid_star_24px,
                R.string.following,
                data.following.toString()
            )
        }

        Spacer(modifier = Modifier.requiredHeight(20.dp))
        Text(text = stringResource(R.string.bio))
        Text(text = data.bio ?: "")

        Spacer(modifier = Modifier.requiredHeight(20.dp))
        Text(text = stringResource(R.string.blog))
        Text(text = data.blog ?: "")
    }
}

@Composable
private fun FollowView(modifier: Modifier, icon: Int, text: Int, value: String) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)
                .background(Color.LightGray),
            painter = painterResource(id = icon),
            contentScale = ContentScale.Inside,
            contentDescription = stringResource(text)
        )
        Text(text = value, fontWeight = FontWeight.Bold)
        Text(text = stringResource(text))
    }
}