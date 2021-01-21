package com.example.desafiogabriela.repository.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.desafiogabriela.FakeLogger
import com.example.desafiogabriela.api.WebClient
import com.example.desafiogabriela.model.ItemRepository
import com.example.desafiogabriela.model.Items
import com.example.desafiogabriela.model.Owner
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.mock.Calls
import java.io.IOException

class RepositoryViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeLogger = FakeLogger()
    private val mockWebClient = mockk<WebClient>()
    private val viewModel = RepositoryViewModel(mockWebClient, fakeLogger)

    @Test
    fun givenResponseSuccessful_shouldFillResultListLiveData() {
        val resultList = createFakeReposList()
        // arrange
        every {
            mockWebClient.search(any())
        } returns Calls.response(Items(resultList))

        // act
        viewModel.getSearch()

        // assert
        assertEquals(resultList, viewModel.liveData.value)
    }

    @Test
    fun givenNetworkError_shouldReturnError() {
        val exceptionThrown = IOException()
        every {
            mockWebClient.search(any())
        } returns Calls.failure(exceptionThrown)

        viewModel.getSearch()

        assertEquals(exceptionThrown, viewModel.liveDataNetworkError.value)
    }

    private fun createFakeReposList(): List<ItemRepository> {
        return (1..10).map { number ->
            ItemRepository(
                nameRepository = "Repo $number",
                description = "Description $number",
                forksCount = number,
                starsCount = number * 2,
                owner = Owner(image = "https://google.com", username = "joao"),
                fullname = "Joao $number"
            )
        }
    }
}

/*
Items(
 items = repository1, repository2, repository3
)
 */