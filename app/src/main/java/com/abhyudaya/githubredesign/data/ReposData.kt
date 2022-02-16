package com.abhyudaya.githubredesign.data

data class ReposData(
    val name: String,
    val url: String,
    val description: String,
    val language: String,
    val forks_count: Int,
    val stargazers_count: Int,
    val updated_at: String
)