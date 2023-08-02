package uz.project.hunarbrand.main_fragment.main.products.view_model

import retrofit2.Call
import retrofit2.http.GET

interface GetAllProductApi {
    @GET("products/")
    fun getProducts(): Call<Any>
}