package ru.melnikov.githubsearcher.presentation.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController

fun NavHostController.popUp() {
    if (this.canGoBack)
        this.popBackStack()
}

fun NavHostController.navigateTo(route: Any) {
    this.navigate(route) {
        launchSingleTop = true
    }
}

val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED