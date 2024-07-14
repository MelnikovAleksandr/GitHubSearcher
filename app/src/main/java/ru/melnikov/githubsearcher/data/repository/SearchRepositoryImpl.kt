package ru.melnikov.githubsearcher.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.melnikov.githubsearcher.data.paging.UserPagingSource
import ru.melnikov.githubsearcher.data.remote.GitHubApi
import ru.melnikov.githubsearcher.domain.model.User
import ru.melnikov.githubsearcher.domain.repository.SearchRepository
import ru.melnikov.githubsearcher.utils.Constants.PAGING_SIZE

class SearchRepositoryImpl(
    private val api: GitHubApi,
) : SearchRepository {
    override suspend fun searchGithubUsers(
        keyword: String
    ): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                UserPagingSource(
                    api,
                    query = keyword
                )
            }
        ).flow
    }
}