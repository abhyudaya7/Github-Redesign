package com.abhyudaya.githubredesign.retrofit

import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.data.ProfileData
import com.abhyudaya.githubredesign.data.ReposData
import retrofit2.Response

class RepositoryImpl(private val service: ProfileApiInterface): Repository {
    override suspend fun getProfileData(user: String): Response<ProfileData> = service.getProfileData(user)

    override suspend fun getReposData(user: String): Response<List<ReposData>> = service.getReposData(user)

    override suspend fun getContributorData(
        user: String,
        repo: String
    ): Response<List<ContributorData>> = service.getContributorData(user,repo)
}