package com.example.desafiogabriela.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryActivityTest {
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        repositoryArrange(mockWebServer){
            registerIdlingResource()
        }
    }

    @After
    fun teardown() {
        repositoryArrange(mockWebServer){
            shutdownServer()
        }
    }

    @Test
    fun givenRequestSuccessful_shouldRenderRepositoriesList() {
        repositoryArrange(mockWebServer){
            enqueueResponse("repositories.json")
            startServer()
            startRepositoriesScreen()
        }

        repositoryAssert{
            checkTextIsVisible("CS-Notes")
        }
    }
}