package com.example.desafiogabriela.utils

import android.util.Log

class LoggerAndroid: Logger {
    override fun logMessage(tag: String, message: String) {
        Log.d(tag, message)
    }
}