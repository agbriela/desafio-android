package com.example.desafiogabriela.api

import androidx.annotation.VisibleForTesting
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitLauncher {
    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }
    val okHttpClient =  OkHttpClient.Builder().addInterceptor(interceptor).build()

    @VisibleForTesting
    var baseUrl = "https://api.github.com/"

    fun get(): WebClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebClient::class.java)
    }
}
