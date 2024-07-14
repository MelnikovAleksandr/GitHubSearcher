package ru.melnikov.githubsearcher.domain.repository

import ru.melnikov.githubsearcher.domain.model.GithubUser
import ru.melnikov.githubsearcher.utils.Resource

interface GitHubUserRepository {

    suspend fun getUserInfo(): Resource<GithubUser>

}