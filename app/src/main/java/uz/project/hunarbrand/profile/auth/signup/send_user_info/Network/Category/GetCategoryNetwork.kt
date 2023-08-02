package uz.project.hunarbrand.profile.auth.signup.send_user_info.Network.Category

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetCategoryNetwork {

    private val okHttpClient = OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://edumonitoring.uz/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}