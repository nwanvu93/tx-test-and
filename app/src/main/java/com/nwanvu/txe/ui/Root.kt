package com.nwanvu.txe.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nwanvu.txe.ui.screens.detail.UserDetailsScreen
import com.nwanvu.txe.ui.screens.list.UserListScreen
import com.nwanvu.txe.ui.theme.GithubUsersTheme
import kotlinx.serialization.Serializable

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val username: String)

@Composable
fun Root() {
    GithubUsersTheme {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = ListDestination) {
                composable<ListDestination> {
                    UserListScreen(navigateToDetails = { username ->
                        navController.navigate(DetailDestination(username))
                    })
                }
                composable<DetailDestination> { backStackEntry ->
                    UserDetailsScreen(
                        username = backStackEntry.toRoute<DetailDestination>().username,
                        navigateBack = {
                            navController.popBackStack()
                        })
                }
            }
        }
    }
}