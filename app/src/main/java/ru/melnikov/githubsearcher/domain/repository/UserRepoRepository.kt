package ru.melnikov.githubsearcher.domain.repository

import ru.melnikov.githubsearcher.domain.model.Repo
import ru.melnikov.githubsearcher.utils.Resource

interface UserRepoRepository {

    suspend fun getUserRepo(userName: String): Resource<List<Repo>>

}