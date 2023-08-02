package uz.project.hunarbrand.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.DecodedJWT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.project.hunarbrand.cart.view_types.ProductsInCart
import uz.project.hunarbrand.main_fragment.main.types.ProductType

object Bearer {
    fun saveAccessToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("bearer", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("access_token", token)
        editor.apply()
    }

    fun saveRefreshToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("bearer", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("refresh_token", token)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("bearer", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", null)
        return token
    }

    fun getRefreshToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("bearer", Context.MODE_PRIVATE)
        return sharedPreferences.getString("refresh_token", null)
    }



    fun getUserId(context: Context): Int? {
        val token = getAccessToken(context)
        // val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg2MTEwNTUzLCJpYXQiOjE2ODYwMjQxNTMsImp0aSI6Ijg2MTMzZDEwYjY5NjQwYmRiZWI2YTFmMmE5MzAyNjMxIiwidXNlcl9pZCI6Mjg4fQ.zDHXfSHEHWvR6GN0nxliGWTnKkxYp69NO_0ui8bCEjM"

        if (token != null) {
            val jwt: DecodedJWT? = try {
                JWT.decode(token)
            } catch (e: JWTDecodeException) {
                null
            }
            val idUser = jwt?.claims?.get("user_id")?.asInt()
            val exp = jwt?.expiresAt?.time ?: 0
            val currentTime = System.currentTimeMillis()
            return if (exp > currentTime) {
                idUser ?: 0
//                13
            } else {
                null
            }
        }
        return null
    }

    fun addToCart(context: Context, product: ProductType) {
        val sharedPreferences = context.getSharedPreferences("add_to_cart", Context.MODE_PRIVATE)

        val gson = Gson()
        val profileInfoJson = gson.toJson(Bearer.addProduct(context, product))
        sharedPreferences.edit()
            .putString("product_in_cart", profileInfoJson)
            .apply()
    }

    fun getProductsInCart(context: Context): MutableList<ProductsInCart>? {
        val sharedPreferences = context.getSharedPreferences("add_to_cart", Context.MODE_PRIVATE)
        val product = sharedPreferences.getString("product_in_cart", null)
        if (product != null) {
            val gson = Gson()
            return gson.fromJson(
                product,
                object : TypeToken<List<ProductsInCart>>() {}.type
            )
        }
        return null
    }

    fun clearProductsInCart(context: Context) {
        val sharedPreferences = context.getSharedPreferences("add_to_cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    fun deleteProductById(context: Context, id: Int): List<ProductsInCart> {
        val products = getProductsInCart(context)?.toMutableList() ?: mutableListOf()
        products.removeAll { it.id == id }
        val sharedPreferences = context.getSharedPreferences("add_to_cart", Context.MODE_PRIVATE)
        val gson = Gson()
        val profileInfoJson = gson.toJson(products)
        sharedPreferences.edit()
            .putString("product_in_cart", profileInfoJson)
            .apply()

        return products
    }

    private fun addProduct(context: Context, product: ProductType): List<ProductsInCart> {
        val products = getProductsInCart(context)?.toMutableList() ?: mutableListOf()

        // Remove existing product with the same ID, if present
        products.removeAll { it.id == product.id }

        // Create a new ProductsInCart object for the added product
        val productInCart = ProductsInCart(
            product.id,
            product.name_uz?:"Nomi mavjud emas",
            product.price,
            product.soha_uz ?: "Soha mavjud emas",
            product.main_image?: "Rasm mavjud emas",
            1 // Set initial product count to 1
        )

        // Add the new product to the cart
        products.add(productInCart)

        // Logging the updated products
        for (i in products) {
            Log.d("CheckProductInBearer", "After addToCart: ${i.name_uz}")
        }

        return products
    }
}