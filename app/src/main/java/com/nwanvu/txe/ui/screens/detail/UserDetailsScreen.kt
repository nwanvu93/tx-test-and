package com.nwanvu.txe.ui.screens.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nwanvu.txe.R
import com.nwanvu.txe.ui.views.AppBar
import com.nwanvu.txe.ui.views.EmptyView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailsScreen(
    username: String,
    navigateBack: () -> Unit,
) {
//    val viewModel = koinViewModel<DetailViewModel>()
//
//    val obj by viewModel.getObject(objectId).collectAsStateWithLifecycle(initialValue = null)
//    AnimatedContent(obj != null) { objectAvailable ->
//        if (objectAvailable) {
//            ObjectDetails(obj!!, onBackClick = navigateBack)
//        } else {
//            EmptyScreenContent(Modifier.fillMaxSize())
//        }
//    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar(stringResource(R.string.title_details), onBackClick = navigateBack) },
    ) { innerPadding ->
//        EmptyView(
//            Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//        )
        Text(modifier = Modifier.padding(innerPadding), text= "username $username")
    }
}