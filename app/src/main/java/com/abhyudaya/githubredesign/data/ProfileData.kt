package com.abhyudaya.githubredesign.data

data class ProfileData(
    val avatar_url: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val login: String,
    val name: String,
    val public_repos: Int
)