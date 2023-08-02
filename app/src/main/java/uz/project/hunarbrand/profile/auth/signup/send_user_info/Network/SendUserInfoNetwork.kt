package com.example.hunarbrend.signup.SendUserInfo.Network

import uz.project.hunarbrand.profile.auth.signup.otpauth.di.conformcode.AccessInterceptor
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import com.example.hunarbrend.signup.BearerTokenHolder


object SendUserInfoNetwork {

    private val okHttpClient = OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://edumonitoring.uz/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.addInterceptor(AccessInterceptor(BearerTokenHolder.getToken())).build())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
