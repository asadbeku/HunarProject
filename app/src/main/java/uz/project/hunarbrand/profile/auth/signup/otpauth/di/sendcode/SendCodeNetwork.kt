package com.example.hunarbrend.signup.otpauth.di.sendcode

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SendCodeNetwork {

    private val okHttpClient = OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://edumonitoring.uz/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}