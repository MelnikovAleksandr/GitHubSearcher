package ru.melnikov.githubsearcher.data.repository

import ru.melnikov.githubsearcher.BuildConfig
import ru.melnikov.githubsearcher.data.model.AccessTokenRequest
import ru.melnikov.githubsearcher.data.model.toAccessToken
import ru.melnikov.githubsearcher.data.remote.AuthApi
import ru.melnikov.githubsearcher.data.remote.RetrofitErrorsHandler
import ru.melnikov.githubsearcher.domain.model.AccessToken
import ru.melnikov.githubsearcher.domain.repository.AuthRepository
import ru.melnikov.githubsearcher.utils.Resource

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val retrofitErrorsHandler: RetrofitErrorsHandler
) : AuthRepository {
    override suspend fun getAuthToken(code: String): Resource<AccessToken> {
        return retrofitErrorsHandler.executeSafely {
            val response =
                api.getAccessToken(
                    AccessTokenRequest(
                        client_id = BuildConfig.ID,
                        client_secret = BuildConfig.SECRET,
                        code = code
                    )
                )
            if (response.isSuccessful && response.code() == 200) {
                val token = response.body()?.toAccessToken()
                if (token != null) {
                    Resource.Success(token)
                } else {
                    Resource.Error()
                }
            } else {
                retrofitErrorsHandler.responseFailureHandler(response)
            }
        }
    }
}