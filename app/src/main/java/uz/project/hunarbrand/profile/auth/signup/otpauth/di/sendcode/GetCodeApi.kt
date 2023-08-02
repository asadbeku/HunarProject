package uz.project.hunarbrand.profile.auth.signup.otpauth.di.sendcode

import uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.requestdata.GetCodeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GetCodeApi {
    @POST("signup/")
    fun sendCode(@Body userData: GetCodeRequest): Call<Any>
}