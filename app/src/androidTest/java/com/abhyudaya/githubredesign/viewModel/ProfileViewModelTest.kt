package com.abhyudaya.githubredesign.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhyudaya.githubredesign.data.ProfileData
import com.abhyudaya.githubredesign.data.ReposData
import com.abhyudaya.githubredesign.retrofit.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response


@RunWith(JUnit4::class)
class ProfileViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var user: String
    private lateinit var viewModel: ProfileViewModel
    private var mockRepository = mockk<Repository>()

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
            assertTrue(repoList.size == 2)
        }
    }


    @Before
    fun setup() {
        user = "greenrobot"
//        val repository = FakeRepositoryImpl(RetrofitFactory.makeRetrofitService())

        val profileResp: Response<ProfileData> = Response.success(
            ProfileData(
                login = user,
                avatar_url = "/",
                followers = 100,
                following = 100,
                bio = "Fake bio",
                name = "Fake name",
                public_repos = 100
            )
        )

        val repoResponse: Response<List<ReposData>> = Response.success(
            listOf(
                ReposData(
                    name = "Repo 1",
                    url = "/",
                    description = "Fake repo",
                    language = "Kotlin",
                    forks_count = 12,
                    stargazers_count = 24,
                    updated_at = "Never updated",
                    topics = emptyList()
                )
                ,ReposData(
                    name = "Repo 2",
                    url = "/",
                    description = "Fake repo 2",
                    language = "Kotlin",
                    forks_count = 69,
                    stargazers_count = 72,
                    updated_at = "Never",
                    topics = emptyList()
                ))
        )

        coEvery {
            mockRepository.getProfileData(user)
        } returns profileResp

        coEvery {
            mockRepository.getReposData(user)
        } returns repoResponse

        viewModel = ProfileViewModel(mockRepository)
        runBlocking {
            viewModel.getDataFromApi(user)
        }
    }
}