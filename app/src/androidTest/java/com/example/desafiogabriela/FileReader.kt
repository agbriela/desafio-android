package com.example.desafiogabriela

import androidx.test.platform.app.InstrumentationRegistry

fun String.loadAsFixture(): String {
    val context = InstrumentationRegistry.getInstrumentation().context
    return context.assets
        .open("fixtures/$this")
        .bufferedReader()
        .readText()
}