package uz.project.hunarbrand.cart.view_model

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.cart.network.CartApi
import uz.project.hunarbrand.cart.network.CartNetwork
import uz.project.hunarbrand.cart.view_types.CartType
import uz.project.hunarbrand.cart.view_types.ProductsInCart
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.main_fragment.detail.view_types.OrderLinkType
import uz.project.hunarbrand.main_fragment.detail.view_types.OrdersType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import uz.project.hunarbrand.utils.Bearer
import java.lang.Exception

class CartRepository {

    private val cartDao = Database.instance.cartDao()
    private val productDao = Database.instance.productDao()
    private val profileDao = Database.instance.profileDao()

    suspend fun deleteProductById(id: Int, response: (List<ProductsInCart>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val product = cartDao.getProductById(id)
                cartDao.deleteProductFromCart(listOf(product))

                val allProductsInCart = cartDao.getAllProductsFromCart()
                val productsInCart = mutableListOf<ProductsInCart>()

                for (sortProduct in allProductsInCart) {
                    val productById = productDao.getProductById(sortProduct.id)
                    productsInCart += ProductsInCart(
                        productById.id,
                        productById.name_uz ?: "Nomi mavjud emas",
                        productById.price,
                        productById.soha_uz ?: "Soha mavjud emas",
                        productById.main_image ?: "Rasm mavjud emas",
                        cartDao.getProductById(productById.id).count
                    )
                }

                response(productsInCart)
            } catch (e: Exception) {
                Log.d("DBException", e.toString())
            }
        }
    }

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

    suspend fun incrementProductCount(id: Int, response: (List<ProductsInCart>) -> Unit) {
        withContext(Dispatchers.Main) {
            try {
                val product = cartDao.getProductById(id)
                cartDao.changeProductCount(listOf(CartType(product.id, product.count + 1)))

                val allProductsInCart = cartDao.getAllProductsFromCart()
                val productsInCart = mutableListOf<ProductsInCart>()

                for (sortProduct in allProductsInCart) {
                    val productById = productDao.getProductById(sortProduct.id)
                    productsInCart += ProductsInCart(
                        productById.id,
                        productById.name_uz ?: "Nomi mavjud emas",
                        productById.price,
                        productById.soha_uz ?: "Soha mavjud emas",
                        productById.main_image ?: "Rasm mavjud emas",
                        cartDao.getProductById(productById.id).count
                    )
                }

                response(productsInCart)
            } catch (e: Exception) {
                Log.d("DBException", e.toString())
            }


        }
    }

    suspend fun decrementProductCount(id: Int, response: (List<ProductsInCart>) -> Unit) {
        withContext(Dispatchers.Main) {

            try {
                val product = cartDao.getProductById(id)
                if (product.count > 1) {
                    cartDao.changeProductCount(listOf(CartType(product.id, product.count - 1)))

                    val allProductsInCart = cartDao.getAllProductsFromCart()
                    val productsInCart = mutableListOf<ProductsInCart>()

                    for (sortProduct in allProductsInCart) {
                        val productById = productDao.getProductById(sortProduct.id)
                        productsInCart += ProductsInCart(
                            productById.id,
                            productById.name_uz ?: "Nomi mavjud emas",
                            productById.price,
                            productById.soha_uz ?: "Soha mavjud emas",
                            productById.main_image ?: "Rasm mavjud emas",
                            cartDao.getProductById(productById.id).count
                        )
                    }

                    response(productsInCart)
                } else {
                    Log.d("ProductCount", "Product count is not be under 1")
                }
            } catch (e: Exception) {
                Log.d("DBException", e.toString())
            }


        }
    }

    suspend fun removeProduct(id: Int) {
        try {

        } catch (e: Exception) {
            Log.d("DBException", e.toString())
        }
    }

    fun updateProduct() {
        TODO()
    }

    suspend fun getCartProducts(response: (List<ProductsInCart>) -> Unit) {
        withContext(Dispatchers.Main) {
            try {
                val allProductsByIds = cartDao.getAllProductsFromCart()
                val productList = mutableListOf<ProductsInCart>()

                for (product in allProductsByIds) {
                    val productFromDB = productDao.getProductById(product.id)
                    productList += productFromDB.let {
                        ProductsInCart(
                            it.id,
                            it.name_uz ?: "Nomi mavjud emas",
                            it.price,
                            it.soha_uz ?: "Soha mavjud emas",
                            it.main_image ?: "Rasm mavjud emas",
                            product.count
                        )
                    }
                }

                response(productList)

            } catch (e: Exception) {
                Log.d("DBException", "$e")
            }
        }

    }

    fun buyProducts(json: RequestBody, responseOrder: (product: OrdersType) -> Unit) {
        CartNetwork.buildOrderService(CartApi::class.java).createOrder(json).enqueue(
            object : Callback<OrdersType> {
                override fun onResponse(call: Call<OrdersType>, response: Response<OrdersType>) {
                    if (response.body() != null) {
                        responseOrder(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<OrdersType>, t: Throwable) {
                    Log.d("checkBuyProductsFail", "onFailure: ${t.message}")
                }

            }
        )
    }

    fun createLink(json: RequestBody, responseLink: (link: OrderLinkType) -> Unit) {
        CartNetwork.buildOrderService(CartApi::class.java).createLink(json).enqueue(
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

            })
    }

}