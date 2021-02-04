package com.example.desafiogabriela.utils

import com.example.desafiogabriela.utils.log.Logger

class FakeLogger : Logger {
    override fun logMessage(tag: String, message: String) {
    }
}