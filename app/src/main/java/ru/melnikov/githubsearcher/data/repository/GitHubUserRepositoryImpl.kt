package ru.melnikov.githubsearcher.data.repository

import ru.melnikov.githubsearcher.data.model.toDomain
import ru.melnikov.githubsearcher.data.remote.GitHubApi
import ru.melnikov.githubsearcher.data.remote.RetrofitErrorsHandler
import ru.melnikov.githubsearcher.domain.model.GithubUser
import ru.melnikov.githubsearcher.domain.repository.GitHubUserRepository
import ru.melnikov.githubsearcher.utils.Resource

class GitHubUserRepositoryImpl(
    private val api: GitHubApi,
    private val retrofitErrorsHandler: RetrofitErrorsHandler
) : GitHubUserRepository {
    override suspend fun getUserInfo(): Resource<GithubUser> {
        return retrofitErrorsHandler.executeSafely {
            val response = api.getProfile()
            if (response.isSuccessful && response.code() == 200) {
                val profile = response.body()?.toDomain()
                if (profile != null) {
                    Resource.Success(profile)
                } else {
                    Resource.Error()
                }
            } else {
                retrofitErrorsHandler.responseFailureHandler(response)
            }
        }
    }
}