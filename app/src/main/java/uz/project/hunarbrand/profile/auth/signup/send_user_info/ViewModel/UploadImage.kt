package com.example.hunarbrend.signup.SendUserInfo.ViewModel

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadImage {
    companion object {
        fun getMultipartBody(name: String, file: File): MultipartBody.Part {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(name, file.name, requestFile)
        }
    }
}