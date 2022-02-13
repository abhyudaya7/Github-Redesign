package com.abhyudaya.githubredesign

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ProfileApiInterface{
    @GET
    suspend fun getProfileData(@Url str:String): Response<ProfileData>
    @GET("repos")
    suspend fun getReposData(): Response<List<ReposData>>
}