package com.example.desafiogabriela.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.desafiogabriela.utils.MockServerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryActivityTest {

    @get:Rule
    val mockWebServerRule = MockServerRule()

    @Test
    fun givenRequestSuccessful_shouldRenderRepositoriesList() {
        repositoryArrange(mockWebServerRule) {
            enqueueResponse("repositories.json")
            startRepositoriesScreen()
        }
        repositoryAssert {
            checkTextVisible("CS-Notes")
        }
    }

    @Test
    fun givenNetworkError_shouldReturnErrorAlertDialog() {
        repositoryArrange(mockWebServerRule) {
            enqueueNetworkError()
            startRepositoriesScreen()
        }
        repositoryAssert {
            checkTextVisible("Erro no servidor")
        }
    }

    @Test
    fun givenFailureResponse_shouldReturnErrorAlertDialog() {
        repositoryArrange(mockWebServerRule) {
            enqueueResponseError(Throwable())
            startRepositoriesScreen()
        }
        repositoryAssert {
            checkTextVisible("Erro desconhecido")
        }
    }
}
