package ru.melnikov.githubsearcher.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.melnikov.githubsearcher.presentation.screens.SearchScreen

@Composable
fun NavigationGraph(
    paddingValues: PaddingValues
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.UserSearchScreen,
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {

        composable<Routes.UserSearchScreen> {
            SearchScreen()
        }

    }

}