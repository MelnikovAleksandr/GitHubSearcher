package ru.melnikov.githubsearcher.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.melnikov.githubsearcher.data.model.FollowerDto
import ru.melnikov.githubsearcher.data.model.UserItems

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") keyword: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int,
    ): UserItems

    @GET
    suspend fun getFollowers(@Url url: String): List<FollowerDto>
}