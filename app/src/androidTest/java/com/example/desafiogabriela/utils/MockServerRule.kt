package com.example.desafiogabriela.utils

import androidx.test.espresso.IdlingRegistry
import com.example.desafiogabriela.api.RetrofitLauncher
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockServerRule: TestWatcher() {
    val mockWebServer = MockWebServer()

    override fun starting(description: Description?) {
        super.starting(description)

        IdlingRegistry.getInstance().register(OkHttp3IdlingResourceAndroidX.create("Okhttp", RetrofitLauncher.client ))

        mockWebServer.start(8080)

        RetrofitLauncher.baseurl = mockWebServer.url("/").toString()

    }

    override fun finished(description: Description?) {
        super.finished(description)
        mockWebServer.shutdown()
    }
}