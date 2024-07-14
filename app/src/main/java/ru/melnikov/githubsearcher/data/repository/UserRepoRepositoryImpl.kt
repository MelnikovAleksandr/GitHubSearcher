package ru.melnikov.githubsearcher.data.repository

import ru.melnikov.githubsearcher.data.model.toRepo
import ru.melnikov.githubsearcher.data.remote.GitHubApi
import ru.melnikov.githubsearcher.data.remote.RetrofitErrorsHandler
import ru.melnikov.githubsearcher.domain.model.Repo
import ru.melnikov.githubsearcher.domain.repository.UserRepoRepository
import ru.melnikov.githubsearcher.utils.Resource

class UserRepoRepositoryImpl(
    private val api: GitHubApi,
    private val retrofitErrorsHandler: RetrofitErrorsHandler
) : UserRepoRepository {
    override suspend fun getUserRepo(userName: String): Resource<List<Repo>> {
        return retrofitErrorsHandler.executeSafely {
            val response =
                api.getUserRepos(userName)
            if (response.isSuccessful && response.code() == 200) {
                val repos = response.body()?.map { it.toRepo() }
                Resource.Success(repos ?: emptyList())
            } else {
                retrofitErrorsHandler.responseFailureHandler(response)
            }
        }
    }
}