package com.abhyudaya.githubredesign.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhyudaya.githubredesign.data.ContributorData
import com.abhyudaya.githubredesign.retrofit.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ContributorViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ContributorViewModel
    private lateinit var contributorResp: Response<List<ContributorData>>
    private var mockRepository: Repository = mockk()

    @Before
    fun setup() {
        contributorResp =
            Response.success(listOf(
                ContributorData(
                    login = "Contributor 1",
                    avatar_url = "/",
                    contributions = "45"
                ),
                ContributorData(
                    login = "Contributor 2",
                    avatar_url = "/",
                    contributions = "99"
                )
            ))

//        mockRepository = mockk<Repository>()

    }

    @Test
    fun checkContributorListUpdated() {
        val userName = "Fake user"
        val repoName = "Fake repo"

        coEvery {
            mockRepository.getContributorData(userName, repoName)
        } returns contributorResp

        viewModel = ContributorViewModel(mockRepository)
        runBlocking {
            viewModel.getContributorFromApi(userName, repoName)
        }

        val contributorList = viewModel.contributorList.value

        assertEquals(contributorResp.body(), contributorList)
    }

}