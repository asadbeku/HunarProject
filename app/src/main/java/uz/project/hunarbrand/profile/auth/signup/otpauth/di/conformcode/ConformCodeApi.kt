package uz.project.hunarbrand.profile.auth.signup.otpauth.di.conformcode

import uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.requestdata.ConformCodeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ConformCodeApi {
    @POST("verify/")
    fun conformCode(
        @Body codeData: ConformCodeRequest
    ): Call<Any>
}