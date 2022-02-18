package com.abhyudaya.githubredesign.retrofit

import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.data.ProfileData
import com.abhyudaya.githubredesign.data.ReposData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiInterface{

    @GET("users/{user}")
    suspend fun getProfileData(@Path("user") userID:String): Response<ProfileData>

    @GET("users/{user}/repos")
    suspend fun getReposData(@Path("user") userID: String): Response<List<ReposData>>

    @GET("repos/{user}/{repo}/contributors")
    suspend fun getContributorData(
        @Path("user") userID: String,
        @Path("repo") repoName: String
    ): Response<List<ContributorData>>
}