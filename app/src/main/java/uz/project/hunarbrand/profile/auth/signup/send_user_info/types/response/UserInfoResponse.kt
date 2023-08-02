package com.example.hunarbrend.signup.SendUserInfo.Types.Response

data class UserInfoResponse(
    val success: String,
    val auth_status: String,
    val access: String,
    val refresh: String
)