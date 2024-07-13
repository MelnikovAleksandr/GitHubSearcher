package ru.melnikov.githubsearcher.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.melnikov.githubsearcher.domain.model.User

interface SearchRepository {
    suspend fun searchGithubUsers(
        keyword: String
    ): Flow<PagingData<User>>
}