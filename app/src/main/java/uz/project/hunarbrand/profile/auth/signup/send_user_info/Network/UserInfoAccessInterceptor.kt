package com.example.hunarbrend.signup.SendUserInfo.Network

import okhttp3.Interceptor
import okhttp3.Response

class UserInfoAccessInterceptor(private val apiKey: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${apiKey.toString()}")
            .build()
        return chain.proceed(request)
    }
}