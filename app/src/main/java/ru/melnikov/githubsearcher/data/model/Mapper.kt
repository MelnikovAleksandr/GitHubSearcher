package ru.melnikov.githubsearcher.data.model

import ru.melnikov.githubsearcher.domain.model.User

fun UserDto.toUser(followers: Int) = User(
    id = id,
    avatarUrl = avatarUrl,
    name = name,
    followers = followers
)