package com.example.desafiogabriela.repository

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.desafiogabriela.api.RetrofitLauncher
import com.example.desafiogabriela.loadAsFixture
import com.example.desafiogabriela.retryer
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class repositoryArrange(
    private val mockWebServer: MockWebServer,
    action: repositoryArrange.() -> Unit
) {
    init {
        action.invoke(this)
    }

    fun registerIdlingResource() {
        IdlingRegistry.getInstance()
            .register(OkHttp3IdlingResource.create("okhttp", RetrofitLauncher.okHttpClient))
    }

    fun startServer() {
        mockWebServer.start(8080)
        RetrofitLauncher.baseUrl = mockWebServer.url("/").toString()
    }

    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    fun enqueueResponse(responseFileName: String) {
        mockWebServer.enqueue(
            MockResponse().setBody(responseFileName.loadAsFixture())
        )
    }

    fun startRepositoriesScreen(){
        ActivityScenario.launch(RepositoryActivity::class.java)
    }
}

class repositoryAct(action: repositoryAct.() -> Unit) {
    init {
        action.invoke(this)
    }
}

class repositoryAssert(action: repositoryAssert.() -> Unit) {
    init {
        action.invoke(this)
    }

    fun checkTextIsVisible(text: String) {
        retryer {
            onView(withText(text))
                .check(matches(isDisplayed()))
        }
    }
}