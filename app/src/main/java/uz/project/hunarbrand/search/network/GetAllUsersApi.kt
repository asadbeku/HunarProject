package uz.project.hunarbrand.search.network

import retrofit2.Call
import retrofit2.http.GET

interface GetAllUsersApi {

    @GET("users/")
    fun getUsers(): Call<Any>

}