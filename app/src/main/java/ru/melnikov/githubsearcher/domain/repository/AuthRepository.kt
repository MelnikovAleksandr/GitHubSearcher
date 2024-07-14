package ru.melnikov.githubsearcher.domain.repository

import ru.melnikov.githubsearcher.domain.model.AccessToken
import ru.melnikov.githubsearcher.utils.Resource

interface AuthRepository {

    suspend fun getAuthToken(code: String): Resource<AccessToken>

}