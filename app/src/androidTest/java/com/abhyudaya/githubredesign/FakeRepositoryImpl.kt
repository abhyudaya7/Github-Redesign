package com.abhyudaya.githubredesign

import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.data.ProfileData
import com.abhyudaya.githubredesign.data.ReposData
import com.abhyudaya.githubredesign.retrofit.ProfileApiInterface
import com.abhyudaya.githubredesign.retrofit.Repository
import retrofit2.Response

class FakeRepositoryImpl(private val service: ProfileApiInterface): Repository {
    override suspend fun getProfileData(user: String): Response<ProfileData> =
        Response.success(
            ProfileData(
                login = user,
                avatar_url = "/",
                followers = 100,
                following = 100,
                bio = "Fake bio",
                name = "Fake name",
                public_repos = 100
            )
        )
    override suspend fun getReposData(user: String): Response<List<ReposData>> =
        Response.success(
            listOf(
                ReposData(
                    name = "Repo 1",
                    url = "/",
                    description = "Fake repo",
                    language = "Kotlin",
                    forks_count = 12,
                    stargazers_count = 24,
                    updated_at = "Never updated",
                    topics = emptyList()
                )
                , ReposData(
                    name = "Repo 2",
                    url = "/",
                    description = "Fake repo 2",
                    language = "Kotlin",
                    forks_count = 69,
                    stargazers_count = 72,
                    updated_at = "Never",
                    topics = emptyList()
                )
            )
        )

    override suspend fun getContributorData(
        user: String,
        repo: String
    ): Response<List<ContributorData>> = Response.success(emptyList())
}