package ru.melnikov.githubsearcher.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import ru.melnikov.githubsearcher.data.model.FollowerDto
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
}