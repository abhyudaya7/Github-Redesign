package com.abhyudaya.githubredesign.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.retrofit.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContributorViewModel: ViewModel() {
    lateinit var repoName: String
    lateinit var url: String
    private val _contributorList = MutableLiveData<List<ContributorData>>(emptyList())
    val contributorList: LiveData<List<ContributorData>> get() = _contributorList


    fun getContributorFromApi() {
        RetrofitFactory.BASE_URL = "${url}/"
        val contributorService = RetrofitFactory.makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {
            val contributorResponse = contributorService.getContributorData()
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
        }
    }
}