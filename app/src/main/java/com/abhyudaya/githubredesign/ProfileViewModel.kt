package com.abhyudaya.githubredesign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class ProfileViewModel: ViewModel() {

    private val _name = MutableLiveData<String>("")
    val name: LiveData<String> get() = _name
    private val _login = MutableLiveData<String>("")
    val login: LiveData<String> get() = _login
    private val _bio = MutableLiveData<String>("")
    val bio: LiveData<String> get() = _bio
    private val _followers = MutableLiveData<Int>(-1)
    val followers: LiveData<Int> get() = _followers
    private val _following = MutableLiveData<Int>(-1)
    val following : LiveData<Int> get() = _following
    private val _avatarUrl = MutableLiveData<String>("/")
    val avatarUrl : LiveData<String> = _avatarUrl


    fun getProfileData(user: String) {
        Log.d("UserName", user)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/users/")
            .build()
            .create(ProfileApiInterface::class.java)

        val retrofitData = retrofitBuilder.getProfileData(user)
        retrofitData.enqueue(object : Callback<ProfileData?> {
            override fun onResponse(call: Call<ProfileData?>, response: Response<ProfileData?>) {
                val responseBody = response.body()!!
                Log.d("Abhyu", "Request success")
                _name.value = responseBody.name
                _login.value = responseBody.login
                _bio.value = responseBody.bio
                _followers.value = responseBody.followers
                _avatarUrl.value = responseBody.avatar_url
                _following.value = responseBody.following
            }

            override fun onFailure(call: Call<ProfileData?>, t: Throwable) {
                Log.d("ProfileFragment", "${t.message}")
            }
        })

    }

}