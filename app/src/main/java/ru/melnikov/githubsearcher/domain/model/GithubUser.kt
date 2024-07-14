package ru.melnikov.githubsearcher.domain.model

data class GithubUser(
    val repos: Int,
    val gists: Int,
    val name: String,
    val image: String,
    val followers: Int,
    val following: Int,
)