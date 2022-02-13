package com.abhyudaya.githubredesign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileViewModel: ViewModel() {

    private val _name = MutableLiveData<String>("")
    val name: LiveData<String> get() = _name
    private val _login = MutableLiveData<String>("")
    val login: LiveData<String> get() = _login
    private val _bio = MutableLiveData<String>("")
    val bio: LiveData<String> get() = _bio
    private val _followers = MutableLiveData<Int>(0)
    val followers: LiveData<Int> get() = _followers
    private val _following = MutableLiveData<Int>(0)
    val following : LiveData<Int> get() = _following
    private val _avatarUrl = MutableLiveData<String>("/")
    val avatarUrl : LiveData<String> = _avatarUrl
    private val _repositoriesData = MutableLiveData<Int>(0)
    val repositoriesData: LiveData<Int> get() = _repositoriesData
    private val _starCount = MutableLiveData<Int>(0)
    val starCount: LiveData<Int> get() = _starCount

    lateinit var user: String
    lateinit var repositoryData: List<ReposData>
    fun getDataFromApi() {
        RetrofitFactory.BASE_URL = "https://api.github.com/users/"
        val profileService = RetrofitFactory.makeRetrofitService()
        RetrofitFactory.BASE_URL = "https://api.github.com/users/$user/"
        val repoService = RetrofitFactory.makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {
            val profileResponse = profileService.getProfileData(user)
            val repoResponse = repoService.getReposData()

            withContext(Dispatchers.Main) {
                try{
                    if (profileResponse.isSuccessful and repoResponse.isSuccessful) {
                        val profileBody = profileResponse.body()!!
                        val repoBody = repoResponse.body()!!
                        _name.value = profileBody.name
                        _login.value = profileBody.login
                        _bio.value = profileBody.bio
                        _followers.value = profileBody.followers
                        _avatarUrl.value = profileBody.avatar_url
                        _following.value = profileBody.following
                        _repositoriesData.value = repoBody.size
                        var star = 0
                        for (repo in repoBody)
                            star += repo.stargazers_count
                        _starCount.value = star
                        repositoryData = repoBody
                    }
                }
                catch(t: Throwable) {
                    Log.d("FetchError", "${t.message}")
                }
            }
        }
    }
}