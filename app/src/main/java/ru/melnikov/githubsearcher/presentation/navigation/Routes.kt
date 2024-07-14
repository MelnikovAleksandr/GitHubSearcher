package ru.melnikov.githubsearcher.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object UserSearchScreen : Routes()

    @Serializable
    data class UserRepositoriesScreen(
        val userName: String
    ) : Routes()

    @Serializable
    data object ProfileScreen : Routes()
}