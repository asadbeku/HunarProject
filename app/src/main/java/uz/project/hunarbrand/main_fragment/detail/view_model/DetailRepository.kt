package uz.project.hunarbrand.main_fragment.detail.view_model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.cart.view_types.CartType
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.main_fragment.detail.network.DetailApi
import uz.project.hunarbrand.main_fragment.detail.network.DetailNetwork
import uz.project.hunarbrand.main_fragment.detail.view_types.LikedProduct
import uz.project.hunarbrand.main_fragment.detail.view_types.OrderLinkType
import uz.project.hunarbrand.main_fragment.detail.view_types.OrdersType
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import uz.project.hunarbrand.search.type_view.UserType
import java.lang.Exception
import java.lang.NullPointerException

class DetailRepository {

    private val favouriteProductDao = Database.instance.favouriteProductDao()
    private val productDao = Database.instance.productDao()
    private val profileDao = Database.instance.profileDao()
    private val cartDao = Database.instance.cartDao()

    suspend fun getProfileInfo(
        response: (userInfo: ProfileInfoType) -> Unit,
        errorResponse: (userInfo: String) -> Unit
    ) {
        withContext(Dispatchers.Main) {
            try {
                val profile = profileDao.getProfileInfo()
                response(profile)
            } catch (e: Exception) {
                Log.d("DBException", "$e")
                errorResponse("Siz o'z profilingizga kirishni amalga oshirmagansiz")

            }
        }
    }

    fun getProductInfo(id: Int, response: (product: ProductType) -> Unit) {
        DetailNetwork.buildService(DetailApi::class.java).getProductById(id).enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    val responseBody = response.body()
                    val gson = Gson()
                    if (responseBody != null) {
                        val json = gson.toJson(responseBody)
                        val product = gson.fromJson<ProductType>(
                            json,
                            object : TypeToken<ProductType>() {}.type
                        )
                        response(product)
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    suspend fun likeProduct(id: Int, response: (isLiked: Boolean) -> Unit) {
        try {
            val isLiked: Boolean? = favouriteProductDao.getFavouriteProductById(id)

            if (isLiked != null) {
                if (isLiked) {
                    favouriteProductDao.removeFavouriteProduct(id)
                    response(false)
                } else {
                    favouriteProductDao.addFavouriteProduct(LikedProduct(id))
                    response(true)
                }
            } else {
                favouriteProductDao.addFavouriteProduct(LikedProduct(id))
                response(true)
            }
        } catch (e: Exception) {
            Log.d("DBException", "$e")
        }


    }

    suspend fun addToCart(id: Int, response: (isAdded: Boolean) -> Unit) {
        withContext(Dispatchers.Main) {
            try {
                cartDao.insertProductToCart(listOf(CartType(id, 1)))
            } catch (e: Exception) {
                Log.d("DBException", "$e")
            }
        }
    }

    suspend fun isLiked(id: Int, response: (isLiked: Boolean) -> Unit) {
        val isLiked: Boolean? = favouriteProductDao.getFavouriteProductById(id)
        if (isLiked != null) {
            response(isLiked)
        } else {
            response(false)
        }
    }


    fun buyProducts(json: RequestBody, responseOrder: (product: OrdersType) -> Unit) {

        DetailNetwork.buildOrderService(DetailApi::class.java).createOrder(json).enqueue(
            object : Callback<OrdersType> {
                override fun onResponse(call: Call<OrdersType>, response: Response<OrdersType>) {

                    if (response.body() != null) {
                        responseOrder(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<OrdersType>, t: Throwable) {
                    Log.d("TagCodeCheck", "onFailure: ${t.message}")
                }

            }
        )

    }

    fun createLink(json: RequestBody, responseLink: (link: OrderLinkType) -> Unit) {
        DetailNetwork.buildOrderService(DetailApi::class.java).createLink(json).enqueue(
            object : Callback<OrderLinkType> {
                override fun onResponse(
                    call: Call<OrderLinkType>,
                    response: Response<OrderLinkType>
                ) {
                    if (response.body() != null) {
                        responseLink(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<OrderLinkType>, t: Throwable) {
                    Log.d("TagCodeCheck", "onFailure: ${t.message}")
                }

            }
        )
    }
}

