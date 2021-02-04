package com.example.desafiogabriela.repository.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.desafiogabriela.utils.FakeLogger
import com.example.desafiogabriela.R
import io.mockk.mockk
import org.junit.Test
import com.example.desafiogabriela.api.WebClient
import com.example.desafiogabriela.model.ItemRepository
import com.example.desafiogabriela.model.Items
import io.mockk.every
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.mock.Calls
import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Response
import java.io.IOException

class RepositoryViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeLogger = FakeLogger()
    private val mockWebClient = mockk<WebClient>()
    private val viewModel = RepositoryViewModel(mockWebClient, fakeLogger)

    @Test
    fun givenResponseSuccessful_shouldFillResultListLiveData() {
        // arrange
        every {
            mockWebClient.search(any())
        } returns Calls.response(Items(emptyList()))

        // act
        viewModel.getSearch()

        // assert
        assertEquals(emptyList<ItemRepository>(), viewModel.liveDataNetworkSuccess.value)
    }

    @Test
    fun givenFailureResponse_whenAPILauncherIsWrong_shouldReturnError() {

        // arrange
        every {
            mockWebClient.search(any())
        } returns Calls.response(Response.error(404, "".toResponseBody()))

        // act
        viewModel.getSearch()

        // arrange
        assertEquals(R.string.error_message, viewModel.liveDataNetworkError.value)
    }

    @Test
    fun givenNetworkError_shouldReturnError() {
        val exceptionThrow = IOException()

        // arrange
        every {
            mockWebClient.search(any())
        } returns Calls.failure(exceptionThrow)

        // act
        viewModel.getSearch()

        //assert
        assertEquals(R.string.network_error, viewModel.liveDataNetworkError.value)
    }
}
