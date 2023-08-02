package uz.project.hunarbrand.main_fragment.detail.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.project.hunarbrand.main_fragment.detail.view_types.OrderLinkType
import uz.project.hunarbrand.main_fragment.detail.view_types.OrdersType

interface DetailApi {
    @GET("product/{id}/")
    fun getProductById(
        @Path("id") id: Int
    ): Call<Any>

    @POST("payme/orders/")
    fun createOrder(
        @Body requestBody: RequestBody
    ): Call<OrdersType>


    @POST("payme/pay-link/")
    fun createLink(
        @Body requestBody: RequestBody
    ): Call<OrderLinkType>
}