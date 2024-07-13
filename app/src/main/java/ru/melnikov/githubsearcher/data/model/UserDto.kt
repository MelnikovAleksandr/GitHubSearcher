package ru.melnikov.githubsearcher.data.model

import com.google.gson.annotations.SerializedName

data class UserItems(
    @SerializedName("items")
    val items: List<UserDto>
)

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("login") val name: String,
    @SerializedName("followers_url") val followersUrl: String
)