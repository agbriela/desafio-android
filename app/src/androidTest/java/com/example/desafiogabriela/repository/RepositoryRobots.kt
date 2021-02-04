package com.example.desafiogabriela.repository

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.desafiogabriela.pull.PullrequestActivity
import com.example.desafiogabriela.utils.*
import okhttp3.mockwebserver.MockResponse
import java.lang.Exception

class repositoryArrange(
    private val mockWebServerRule: MockServerRule,
    action: repositoryArrange.() -> Unit
) {

    init {
        action.invoke(this)
    }

    fun enqueueResponse(responseFileName: String) {
        mockWebServerRule.mockWebServer.enqueue(MockResponse()
            .setBody(responseFileName.loadAsFixture())
        )
    }

    fun enqueueNetworkError() {
        mockWebServerRule.mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.statusError))
    }

    fun enqueueResponseError(t: Throwable) {
        mockWebServerRule.mockWebServer.enqueue(MockResponse())
    }

    fun startRepositoriesScreen() {
        val bundle = bundleOf(Constant.owner to "elastic", Constant.repository to "elasticsearch")
        val intent = Intent(ApplicationProvider.getApplicationContext(), RepositoryActivity::class.java).apply {
            putExtras(bundle)
        }
        ActivityScenario.launch<RepositoryActivity>(intent)
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

    fun checkTextVisible(text: String) {
            onView(withText(text)).check(matches(isDisplayed()))
    }
}
