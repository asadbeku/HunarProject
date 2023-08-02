package com.example.hunarbrend.signup.SendUserInfo.Network

import com.example.hunarbrend.signup.SendUserInfo.Types.Response.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface SendUserInfoApi {
    @Multipart
    @PATCH("change-user-information/")
    fun uploadFile(
        @Part("first_name") name: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("direction") direction: RequestBody,
        @Part("brand_name") brand_name: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("sex") sex: RequestBody,
        @Part("password") password: RequestBody,
        @Part("confirm_password") confirm_password: RequestBody,
        @Part avatar: MultipartBody.Part?,
        @Part brand_logo: MultipartBody.Part?
    ): Call<UserInfoResponse>
}