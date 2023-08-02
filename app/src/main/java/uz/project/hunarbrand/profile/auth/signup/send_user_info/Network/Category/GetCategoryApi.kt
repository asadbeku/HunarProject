package uz.project.hunarbrand.profile.auth.signup.send_user_info.Network.Category

import retrofit2.Call
import retrofit2.http.GET
import uz.project.hunarbrand.profile.auth.signup.send_user_info.types.response.CategoryResponse

interface GetCategoryApi {
    @GET("category/")
    fun getCategory(): Call<List<CategoryResponse>>
}