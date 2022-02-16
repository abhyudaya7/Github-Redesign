package com.abhyudaya.githubredesign.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhyudaya.githubredesign.utils.Utils
import com.abhyudaya.githubredesign.data.ReposData
import com.abhyudaya.githubredesign.retrofit.RetrofitFactory
import kotlinx.coroutines.*


class ProfileViewModel: ViewModel() {

    private val _name = MutableLiveData<String>("")
    val name: LiveData<String> get() = _name
    private val _login = MutableLiveData<String>("")
    val login: LiveData<String> get() = _login
    private val _bio = MutableLiveData<String>("")
    val bio: LiveData<String> get() = _bio
    private val _followers = MutableLiveData<String>("")
    val followers: LiveData<String> get() = _followers
    private val _following = MutableLiveData<String>("")
    val following : LiveData<String> get() = _following
    private val _avatarUrl = MutableLiveData<String>("/")
    val avatarUrl : LiveData<String> = _avatarUrl
    private val _repositoriesData = MutableLiveData<String>("")
    val repositoriesData: LiveData<String> get() = _repositoriesData
    private val _starCount = MutableLiveData<String>("")
    val starCount: LiveData<String> get() = _starCount
    private val _repoList = MutableLiveData<List<ReposData>>()
    val repoList: LiveData<List<ReposData>> get() = _repoList

    init {
        _repoList.value = emptyList()
    }



    lateinit var user: String
    fun getDataFromApi() {
        RetrofitFactory.BASE_URL = "https://api.github.com/users/"
        val profileService = RetrofitFactory.makeRetrofitService()
        RetrofitFactory.BASE_URL = "https://api.github.com/users/$user/"
        val repoService = RetrofitFactory.makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {
            
            val profileRespJob = async {
                profileService.getProfileData(user)
            }

            val repoRespJob = async {
                repoService.getReposData()
            }

            val profileResponse = profileRespJob.await()
            val repoResponse = repoRespJob.await()

            withContext(Dispatchers.Main) {
                try{
                    if (profileResponse.isSuccessful and repoResponse.isSuccessful) {
                        val profileBody = profileResponse.body()!!
                        val repoBody = repoResponse.body()!!
                        _name.value = profileBody.name
                        _login.value = profileBody.login
                        _bio.value = profileBody.bio
                        _followers.value = Utils().getFormattedData(profileBody.followers)
                        _avatarUrl.value = profileBody.avatar_url
                        _following.value = Utils().getFormattedData(profileBody.following)
                        _repositoriesData.value = Utils().getFormattedData(repoBody.size)
                        var star = 0
                        for (repo in repoBody)
                            star += repo.stargazers_count
                        _starCount.value = Utils().getFormattedData(star)
                        _repoList.value = repoBody
                    }
                }
                catch(t: Throwable) {
                    Log.d("FetchError", "${t.message}")
                }
            }
        }
    }
}