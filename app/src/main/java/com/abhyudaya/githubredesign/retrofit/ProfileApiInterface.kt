package com.abhyudaya.githubredesign.retrofit

import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.data.ProfileData
import com.abhyudaya.githubredesign.data.ReposData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ProfileApiInterface{

    @GET
    suspend fun getProfileData(@Url str:String): Response<ProfileData>

    @GET("repos")
    suspend fun getReposData(): Response<List<ReposData>>

    @GET("contributors")
    suspend fun getContributorData(): Response<List<ContributorData>>
}