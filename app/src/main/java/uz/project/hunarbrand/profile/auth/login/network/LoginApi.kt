package uz.project.hunarbrand.profile.auth.login.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    @FormUrlEncoded
    @POST("login/")
    fun login(
        @Field("phone_number") phoneNumber: String,
        @Field("password") password: String
    ): Call<Any>

    @FormUrlEncoded
    @POST("login/refresh/")
    fun refreshToken(
        @Field("refresh") refreshToken: String
    ): Call<Any>
}