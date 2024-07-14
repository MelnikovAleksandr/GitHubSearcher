package ru.melnikov.githubsearcher.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.melnikov.githubsearcher.data.model.AccessTokenDto
import ru.melnikov.githubsearcher.data.model.AccessTokenRequest

interface AuthApi {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(
        @Body request: AccessTokenRequest
    ): Response<AccessTokenDto>
}