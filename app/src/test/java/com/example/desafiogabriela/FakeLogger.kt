package com.example.desafiogabriela

import com.example.desafiogabriela.utils.Logger

class FakeLogger : Logger {
    override fun logMessage(tag: String, message: String) {
        // Não faz nada
    }
}