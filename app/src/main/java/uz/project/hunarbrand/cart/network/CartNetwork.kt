package uz.project.hunarbrand.cart.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CartNetwork {
    private val okHttpClient = OkHttpClient.Builder()

    private val createOrder = Retrofit.Builder()
        .baseUrl("https://edumonitoring.uz/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())
        .build()

    fun <T> buildOrderService(service: Class<T>): T {
        return createOrder.create(service)
    }

}