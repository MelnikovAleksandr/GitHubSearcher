package ru.melnikov.githubsearcher.data.model

import kotlinx.serialization.Serializable
import ru.melnikov.githubsearcher.domain.model.GithubUser

@Serializable
data class GithubUserDto(
    val avatar_url: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val login: String,
    val owned_private_repos: Int,
    val private_gists: Int,
    val public_gists: Int,
    val public_repos: Int,
    val total_private_repos: Int,
)

fun GithubUserDto.toDomain(): GithubUser {
    return GithubUser(
        name = login,
        image = avatar_url,
        followers = followers,
        following = following,
        gists = private_gists + public_gists,
        repos = public_repos + total_private_repos,
    )
}