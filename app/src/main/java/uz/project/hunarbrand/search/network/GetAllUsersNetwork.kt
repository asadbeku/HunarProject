package uz.project.hunarbrand.search.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetAllUsersNetwork {

    private val client = OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://edumonitoring.uz/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

}