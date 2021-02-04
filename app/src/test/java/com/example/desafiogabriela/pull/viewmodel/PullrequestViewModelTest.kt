package com.example.desafiogabriela.pull.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.desafiogabriela.utils.FakeLogger
import com.example.desafiogabriela.R
import com.example.desafiogabriela.api.WebClient
import com.example.desafiogabriela.model.ItemPullrequest
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException

class PullrequestViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var owner = ""
    private var repository = ""
    private val fakeLoggerPullTest = FakeLogger()
    private val mockWebClient = mockk<WebClient>()
    private val viewModelPull = PullrequestViewModel(mockWebClient, fakeLoggerPullTest)

    @Test
    fun givenResponseSuccessful_shouldFillResultListLiveData() {
        // arrange
        every {
            mockWebClient.searchPull(owner, repository)
        } returns Calls.response(Response.success(emptyList()))

        // act
        viewModelPull.getSearchPull(owner, repository)

        // arrange
        assertEquals(emptyList<ItemPullrequest>(), viewModelPull.pullLiveDataNetworkSuccess.value)
    }

    @Test
    fun givenFailureResponse_whenAPILauncherIsWrong_shouldFillResultError() {
        // arrange
        every {
            mockWebClient.searchPull(owner, repository)
        } returns Calls.response(Response.error(404, "".toResponseBody()))

        // act
        viewModelPull.getSearchPull(owner, repository)

        // arrange
        assertEquals(R.string.error_message, viewModelPull.pullLiveDataNetworkError.value)
    }

    @Test
    fun givenNetworkError_shouldReturnError() {
        val exceptionThrow = IOException()

        // arrange
        every {
            mockWebClient.searchPull(owner, repository)
        } returns Calls.failure(exceptionThrow)

        // act
        viewModelPull.getSearchPull(owner, repository)

        // arrange
        assertEquals(R.string.network_error, viewModelPull.pullLiveDataNetworkError.value)
    }
}
