package com.abhyudaya.githubredesign

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ProfileApiInterface{
    @GET
    fun getProfileData(@Url str:String): Call<ProfileData>
}