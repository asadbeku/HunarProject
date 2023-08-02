package uz.project.hunarbrand.cart.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import uz.project.hunarbrand.main_fragment.detail.view_types.OrderLinkType
import uz.project.hunarbrand.main_fragment.detail.view_types.OrdersType

interface CartApi {
    @POST("payme/orders/")
    fun createOrder(
        @Body requestBody: RequestBody
    ): Call<OrdersType>

    @POST("payme/pay-link/")
    fun createLink(
        @Body requestBody: RequestBody
    ): Call<OrderLinkType>
}