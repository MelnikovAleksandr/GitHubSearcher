package ru.melnikov.githubsearcher.data.model

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("default_branch")
    val defaultBranch: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("forks_count")
    val forksCount: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("owner")
    val owner: UserDto
)