package ru.melnikov.githubsearcher.data.model

import ru.melnikov.githubsearcher.domain.model.Repo
import ru.melnikov.githubsearcher.domain.model.User
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun UserDto.toUser(followers: Int) = User(
    id = id,
    avatarUrl = avatarUrl,
    name = name,
    followers = followers
)

fun RepoDto.toRepo() =
    Repo(
        defaultBranch = defaultBranch ?: "",
        description = description ?: "",
        forksCount = forksCount ?: 0,
        name = name ?: "",
        id = id,
        language = language ?: "",
        stargazersCount = stargazersCount ?: 0,
        updatedAt = convertDateTime(updatedAt),
        owner = owner.toUser(0)
    )

fun convertDateTime(dateTime: String?): LocalDateTime {
    val zonedDateTime = ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
    return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
}