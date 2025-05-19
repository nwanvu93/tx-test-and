package com.nwanvu.txe.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nwanvu.txe.R
import com.nwanvu.txe.ui.theme.GithubUsersTheme
import com.nwanvu.txe.ui.views.AppBar

@Composable
fun UserListScreen(
    navigateToDetails: (username: String) -> Unit
) = Scaffold(
    modifier = Modifier.fillMaxSize(), topBar = { AppBar(stringResource(R.string.app_name)) },
) { innerPadding ->
//    val viewModel = koinViewModel<ListViewModel>()
//    val objects by viewModel.objects.collectAsStateWithLifecycle()
//
//    AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
//        if (objectsAvailable) {
//            ObjectGrid(
//                objects = objects,
//                onObjectClick = navigateToDetails,
//            )
//        } else {
//            EmptyScreenContent(Modifier.fillMaxSize())
//        }
//    }
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val list = (0..75).map { it.toString() }
        items(count = list.size) {
            Text(
                text = list[it],
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Button(onClick = { navigateToDetails(list[it]) }) {
                Text("Click me")
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GithubUsersTheme {
        Greeting("Android")
    }
}