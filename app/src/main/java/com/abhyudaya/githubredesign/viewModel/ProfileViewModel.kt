package com.abhyudaya.githubredesign.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhyudaya.githubredesign.retrofit.Repository
import com.abhyudaya.githubredesign.utils.Utils
import com.abhyudaya.githubredesign.data.ReposData
import com.abhyudaya.githubredesign.utils.EspressoIdlingResource
import kotlinx.coroutines.*


class ProfileViewModel(private val repository: Repository): ViewModel() {

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
    private val _userNotFound = MutableLiveData<Boolean>(false)
    val userNotFound: LiveData<Boolean> get() = _userNotFound

    init {
        _repoList.value = emptyList()
    }

    suspend fun getDataFromApi(user: String) {
        CoroutineScope(Dispatchers.IO).async {

            val profileRespJob = async {
                EspressoIdlingResource.increment() // incrementing idling res for network call
                repository.getProfileData(user)
            }

            val repoRespJob = async {
                EspressoIdlingResource.increment()
                repository.getReposData(user)
            }

            val profileResponse = profileRespJob.await()
            EspressoIdlingResource.decrement() // decrementing the idling res after network req gets completed
            val repoResponse = repoRespJob.await()
            EspressoIdlingResource.decrement()
            // checking the status code for user not found
            if (profileResponse.code() == 404) {
                _userNotFound.postValue(true)
            }
            withContext(Dispatchers.Main) {
                try{
                    if (profileResponse.isSuccessful and repoResponse.isSuccessful) {
                        val profileBody = profileResponse.body()!!
                        val repoBody = repoResponse.body()!!
                        _name.postValue(profileBody.name)
                        _login.value = profileBody.login
                        _bio.postValue(profileBody.bio)
                        _followers.postValue(Utils().getFormattedData(profileBody.followers))
                        _avatarUrl.postValue(profileBody.avatar_url)
                        _following.postValue(Utils().getFormattedData(profileBody.following))
                        _repositoriesData.postValue(Utils().getFormattedData(profileBody.public_repos))
                        var star = 0
                        for (repo in repoBody)
                            star += repo.stargazers_count
                        _starCount.postValue(Utils().getFormattedData(star))
                        _repoList.postValue(repoBody)
                    }else {
                        Log.d("Response", "Request unsuccessful")
                    }
                }
                catch(t: Throwable) {
                    Log.d("FetchError", "${t.message}")
                }
            }
        }.await()
    }
}