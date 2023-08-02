package com.example.hunarbrend.signup

object BearerTokenHolder {
    private var token: String = ""

    fun getToken(): String {
        return token
    }

    fun setToken(newToken: String) {
        token = newToken
    }
}