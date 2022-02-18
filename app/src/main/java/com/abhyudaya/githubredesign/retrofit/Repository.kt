package com.abhyudaya.githubredesign.retrofit

import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.data.ProfileData
import com.abhyudaya.githubredesign.data.ReposData
import retrofit2.Response

interface Repository {
    suspend fun getProfileData(user: String): Response<ProfileData>
    suspend fun getReposData(user: String): Response<List<ReposData>>
    suspend fun getContributorData(user: String, repo: String): Response<List<ContributorData>>
}