package ru.melnikov.githubsearcher.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.melnikov.githubsearcher.data.model.AccessTokenDto
import ru.melnikov.githubsearcher.data.model.AccessTokenRequest
import ru.melnikov.githubsearcher.data.model.GithubUserDto
import ru.melnikov.githubsearcher.data.model.RepoDto
import ru.melnikov.githubsearcher.data.model.UserDto
import ru.melnikov.githubsearcher.data.model.UserItems

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") keyword: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int,
    ): UserItems

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): Response<List<RepoDto>>

    @GET("users/{username}")
    suspend fun getUserWithFollowers(
        @Path("username") username: String
    ): UserDto

    @GET("user")
    suspend fun getProfile(): Response<GithubUserDto>

}