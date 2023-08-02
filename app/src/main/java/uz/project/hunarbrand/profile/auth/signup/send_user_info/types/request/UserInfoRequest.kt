package com.example.hunarbrend.signup.SendUserInfo.Types.Request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoRequest(
    val first_name: String,
    val auth_status: String,
    val last_name: String,
    val address: String,
    val direction: String,
    val brand_name: String,
    val brand_logo: String,
    val bio: String,
    val sex: String,
    val password: String,
    val confirm_password: String,
    val avatar: String
)