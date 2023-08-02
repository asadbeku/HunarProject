package uz.project.hunarbrand.main_fragment.main.ui.network

import retrofit2.Call
import retrofit2.http.GET

interface GetAllCategoryApi {
    @GET("category/")
    fun getUsers(): Call<Any>
}