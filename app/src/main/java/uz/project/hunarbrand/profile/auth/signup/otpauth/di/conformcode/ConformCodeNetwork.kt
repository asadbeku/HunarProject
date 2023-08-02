package uz.project.hunarbrand.profile.auth.signup.otpauth.di.conformcode

import com.example.hunarbrend.signup.BearerTokenHolder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConformCodeNetwork {
    private val okHttpClient = OkHttpClient.Builder()
    private var bearer = BearerTokenHolder.getToken()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://edumonitoring.uz/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.addInterceptor(AccessInterceptor(bearer)).build())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
