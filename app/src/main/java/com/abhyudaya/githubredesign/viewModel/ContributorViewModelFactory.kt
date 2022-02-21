package com.abhyudaya.githubredesign.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhyudaya.githubredesign.retrofit.Repository
import java.lang.IllegalArgumentException

 class ContributorViewModelFactory(private val repository: Repository)
    :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContributorViewModel::class.java)) {
            return ContributorViewModel(repository) as T
        }
        else {
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}