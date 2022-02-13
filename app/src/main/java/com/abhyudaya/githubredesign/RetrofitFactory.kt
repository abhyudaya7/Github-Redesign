package com.abhyudaya.githubredesign

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    lateinit var BASE_URL: String

    fun makeRetrofitService(): ProfileApiInterface {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ProfileApiInterface::class.java)
    }
}