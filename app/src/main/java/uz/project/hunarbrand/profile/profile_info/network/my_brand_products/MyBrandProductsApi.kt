package uz.project.hunarbrand.profile.profile_info.network.my_brand_products

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import uz.project.hunarbrand.profile.profile_info.view_types.MyProducts

interface MyBrandProductsApi {
    @GET("products/user/get/{id}/")
    fun getProductsById(
        @Path("id") id: Int
    ): Call<MyProducts>
}