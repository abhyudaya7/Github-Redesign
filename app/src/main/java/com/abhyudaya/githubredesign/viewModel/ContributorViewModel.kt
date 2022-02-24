package com.abhyudaya.githubredesign.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhyudaya.githubredesign.retrofit.Repository
import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.utils.EspressoIdlingResource
import kotlinx.coroutines.*


class ContributorViewModel(private val repository: Repository): ViewModel() {
    private val _contributorList = MutableLiveData<List<ContributorData>>(emptyList())
    val contributorList: LiveData<List<ContributorData>> get() = _contributorList


    suspend fun getContributorFromApi(userID: String, repoName: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val contributorRespJob = async {
                EspressoIdlingResource.increment()
                repository.getContributorData(userID, repoName)
            }

            val contributorResponse = contributorRespJob.await()
            EspressoIdlingResource.decrement()
            withContext(Dispatchers.Main) {
                try {
                    if (contributorResponse.isSuccessful) {
                        val contributorData = contributorResponse.body()!!
                        _contributorList.value = contributorData
                    }
                }
                catch(t: Throwable) {

                }
            }
        }.join()
    }
}