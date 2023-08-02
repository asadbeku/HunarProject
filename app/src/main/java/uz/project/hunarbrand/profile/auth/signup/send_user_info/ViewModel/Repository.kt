package com.example.hunarbrend.signup.SendUserInfo.ViewModel

import android.util.Log
import com.example.hunarbrend.signup.SendUserInfo.Network.SendUserInfoApi
import com.example.hunarbrend.signup.SendUserInfo.Network.SendUserInfoNetwork
import com.example.hunarbrend.signup.SendUserInfo.Types.Response.UserInfoResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    fun patchUserInfo(
        name: String,
        lastName: String,
        direction: String,
        brand_name: String,
        bio: String,
        sex: String,
        password: String,
        confirm_password: String,
        brand_logo: MultipartBody.Part?,
        avatar: MultipartBody.Part?,
        callback: (String) -> Unit
    ) {
        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastnameBody = lastName.toRequestBody("text/plain".toMediaTypeOrNull())
        val directionBody = direction.toRequestBody("text/plain".toMediaTypeOrNull())
        val brandNameBody = brand_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val bioBody = bio.toRequestBody("text/plain".toMediaTypeOrNull())
        val sexBody = sex.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
        val confirmPasswordBody =
            confirm_password.toRequestBody("text/plain".toMediaTypeOrNull())

        SendUserInfoNetwork.buildService(SendUserInfoApi::class.java).uploadFile(
            nameBody,
            lastnameBody,
            directionBody,
            brandNameBody,
            bioBody,
            sexBody,
            passwordBody,
            confirmPasswordBody,
            avatar,
            brand_logo
        ).enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                when(response.code()){
                    200,201->{
                        Log.d("checkResponse", "Response: $response")
                        response.body()?.let { callback(it.access) }
                    }
                    400->{
                        callback("null")
                        Log.d("checkResponse", "Response: Response returned false")
                    }
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.d("checkResponse", "Response: $t")
            }
        })
    }
}