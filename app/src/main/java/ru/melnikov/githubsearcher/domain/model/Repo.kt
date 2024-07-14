package ru.melnikov.githubsearcher.domain.model

import java.time.LocalDateTime

data class Repo(
    val defaultBranch: String,
    val description: String,
    val forksCount: Int,
    val name: String,
    val id: Int,
    val language: String,
    val stargazersCount: Int,
    val updatedAt: LocalDateTime,
    val owner: User
)
