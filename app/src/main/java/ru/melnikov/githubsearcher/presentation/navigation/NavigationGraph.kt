package ru.melnikov.githubsearcher.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import ru.melnikov.githubsearcher.presentation.screens.ProfileScreen
import ru.melnikov.githubsearcher.presentation.screens.SearchScreen
import ru.melnikov.githubsearcher.presentation.screens.UserRepoScreen
import ru.melnikov.githubsearcher.utils.Constants.REDIRECT_URI

@Composable
fun NavigationGraph(
    paddingValues: PaddingValues
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.UserSearchScreen,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        composable<Routes.UserSearchScreen>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "${REDIRECT_URI}?code={code}" }
            )
        ) {

            val code = it.arguments?.getString("code")

            SearchScreen(
                code = code,
                navigateToProfile = {
                    navController.navigateTo(Routes.ProfileScreen)
                },
                onNavigateToRepositoryScreen = { userName ->
                    navController.navigateTo(
                        Routes.UserRepositoriesScreen(
                            userName = userName
                        )
                    )

                }
            )
        }

        composable<Routes.UserRepositoriesScreen> {
            UserRepoScreen {
                navController.popUp()
            }
        }

        composable<Routes.ProfileScreen> {
            ProfileScreen {
                navController.popUp()
            }
        }
    }
}