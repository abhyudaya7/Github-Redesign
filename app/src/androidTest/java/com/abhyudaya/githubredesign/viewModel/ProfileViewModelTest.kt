package com.abhyudaya.githubredesign.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhyudaya.githubredesign.getOrAwaitValue
import com.abhyudaya.githubredesign.retrofit.RepositoryImpl
import com.abhyudaya.githubredesign.retrofit.RetrofitFactory
import com.abhyudaya.githubredesign.utils.EspressoIdlingResource
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProfileViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var user: String
    private lateinit var viewModel: ProfileViewModel

    @Test
    fun getLogin() {
        // tests if the username got after api call is same as username passed
        val userReceived = viewModel.login.value
        assertEquals(user, userReceived)

    }

    @Test
    fun getRepoList() {
        // checks if the repoList received after api is not empty
        val repoList = viewModel.repoList.value
        if (repoList != null) {
            assertTrue(repoList.isNotEmpty())
        }
    }


    @Before
    fun setup() {
        user = "greenrobot"
        val repository = RepositoryImpl(RetrofitFactory.makeRetrofitService())
        viewModel = ProfileViewModel(repository)
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).async {
                viewModel.getDataFromApi(user)
            }
            job.await()
        }
    }
}