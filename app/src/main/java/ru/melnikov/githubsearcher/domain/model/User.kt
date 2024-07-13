package ru.melnikov.githubsearcher.domain.model

data class User(
    val id: Int,
    val avatarUrl: String,
    val name: String,
    val followers: Int
)