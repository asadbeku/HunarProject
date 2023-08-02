package uz.project.hunarbrand.splash_screen.view_model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.main_fragment.main.products.view_model.GetAllProductApi
import uz.project.hunarbrand.main_fragment.main.products.view_model.GetAllProductNetwork
import uz.project.hunarbrand.main_fragment.main.types.CategoryType
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.main_fragment.main.ui.network.GetAllCategoryApi
import uz.project.hunarbrand.main_fragment.main.ui.network.GetAllCategoryNetwork
import uz.project.hunarbrand.profile.auth.login.network.LoginApi
import uz.project.hunarbrand.profile.auth.login.network.LoginNetwork
import uz.project.hunarbrand.profile.profile_info.network.profile.ProfileApi
import uz.project.hunarbrand.profile.profile_info.network.profile.ProfileNetwork
import uz.project.hunarbrand.profile.profile_info.view_types.LoginType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import uz.project.hunarbrand.search.network.GetAllUsersApi
import uz.project.hunarbrand.search.network.GetAllUsersNetwork
import uz.project.hunarbrand.search.type_view.UserType
import java.lang.Exception

class SplashRepository {

    fun getProducts(response: (products: List<ProductType>) -> Unit) {

        val productService = GetAllProductNetwork.buildService(GetAllProductApi::class.java)
        productService.getProducts().enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                when (response.code()) {
                    in 200..201 -> {
                        val responseBody = response.body()
                        val gson = Gson()
                        if (responseBody != null) {
                            val json = gson.toJson(responseBody)
                            val productsList: List<ProductType> = gson.fromJson(
                                json,
                                object : TypeToken<List<ProductType>>() {}.type
                            )
                            response(productsList)
                        }
                        Log.d("TagCodeCheck", "${response.body()}")
                    }

                    400 -> {
                        val responseBody = response.body()
                        val gson = Gson()
                        if (responseBody != null) {
                            val json = gson.toJson(responseBody)
                            val productList = gson.fromJson<List<ProductType>>(
                                json,
                                object : TypeToken<List<ProductType>>() {}.type
                            )
                            // Use the productList as needed
                        }
                        Log.d("checkProductFormNetwork", "onResponse (400): ${response.message()}")
                        onFailure(call, Throwable(response.message().toString()))
                    }
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("TagCodeCheck", "onFailure: $t")
            }
        })
    }


    fun getCategories(response: (products: List<CategoryType>) -> Unit) {
        GetAllCategoryNetwork.buildService(GetAllCategoryApi::class.java)
            .getUsers().enqueue(
                object : Callback<Any> {
                    override fun onResponse(
                        call: Call<Any>,
                        response: Response<Any>
                    ) {
                        when (response.code()) {
                            200, 201 -> {
                                val responseBody = response.body()
                                val gson = Gson()
                                if (responseBody != null) {
                                    val json = gson.toJson(responseBody)
                                    val categoryList = gson.fromJson<List<CategoryType>>(
                                        json,
                                        object : TypeToken<List<CategoryType>>() {}.type
                                    )
                                    response(categoryList)
                                }
                                Log.d("TagCodeCheck", "${response.body()}")
                            }

                            400 -> {

                                val responseBody = response.body()
                                val gson = Gson()
                                if (responseBody != null) {
                                    val json = gson.toJson(responseBody)
                                    val productList = gson.fromJson<List<CategoryType>>(
                                        json,
                                        object : TypeToken<List<CategoryType>>() {}.type
                                    )
                                    // Use the productList as needed
                                }

//                                errorMessage(response.toString())

                                onFailure(
                                    call,
                                    Throwable(response.message().toString())
                                )
                                Log.d(
                                    "TagCodeCheck",
                                    "onResponse (400): ${response.message()}"
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.d("TagCodeCheck", "onFailure : $t")
                    }
                }
            )
    }

    fun getProfileInfo(
        id: Int,
        response: (products: ProfileInfoType) -> Unit,
        error: (message: String) -> Unit
    ) {
        ProfileNetwork.buildService(ProfileApi::class.java).getProfileInfo(id).enqueue(
            object : Callback<ProfileInfoType> {
                override fun onResponse(
                    call: Call<ProfileInfoType>,
                    response: Response<ProfileInfoType>
                ) {

                    when (response.code()) {
                        200, 201 -> {
                            val responseBody = response.body()
                            val gson = Gson()
                            if (responseBody != null) {
                                val json = gson.toJson(responseBody)
                                val profileInfo = gson.fromJson<ProfileInfoType>(
                                    json,
                                    object : TypeToken<ProfileInfoType>() {}.type
                                )
                                response(profileInfo)
                            }
                            Log.d("TagCodeCheck", "${response.body()}")
                        }

                        404 -> {
                            error("Foydalanuvchi topilmadi")
                        }

                        500 -> {
                            error("Server bilan ulanishda muammo yuz berdi")
                        }

                        else -> {
                            error("Boshqa xatolik yuz berdi")
                        }

                    }
                }

                override fun onFailure(call: Call<ProfileInfoType>, t: Throwable) {
                    error("Internet bilan bog'lanishda xatolik")
                }
            }
        )
    }

    fun getSearchItems(response: (products: List<UserType>) -> Unit) {
        GetAllUsersNetwork.buildService(GetAllUsersApi::class.java)
            .getUsers().enqueue(
                object : Callback<Any> {
                    override fun onResponse(
                        call: Call<Any>,
                        response: Response<Any>
                    ) {
                        when (response.code()) {
                            200, 201 -> {
                                val responseBody = response.body()
                                val gson = Gson()
                                if (responseBody != null) {
                                    val json = gson.toJson(responseBody)
                                    var responseList = mutableListOf<UserType>()
                                    val usersList = gson.fromJson<List<UserType>>(
                                        json,
                                        object : TypeToken<List<UserType>>() {}.type
                                    )

                                    for (i in usersList) {
                                        if (i.brand_name != null) {
                                            responseList.add(i)
                                        }
                                    }
                                    response(responseList)
                                }
                                Log.d("TagCodeCheck", "${response.body()}")
                            }

                            400 -> {

                                val responseBody = response.body()
                                val gson = Gson()
                                if (responseBody != null) {
                                    val json = gson.toJson(responseBody)
                                    val productList = gson.fromJson<List<UserType>>(
                                        json,
                                        object : TypeToken<List<UserType>>() {}.type
                                    )
                                    // Use the productList as needed
                                }

//                                errorMessage(response.toString())

                                onFailure(
                                    call,
                                    Throwable(response.message().toString())
                                )
                                Log.d(
                                    "TagCodeCheck",
                                    "onResponse (400): ${response.message()}"
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.d("TagCodeCheck", "onFailure : $t")
                    }
                }
            )
    }

    fun refreshToken(refresh: String, response: (LoginType) -> Unit) {
        LoginNetwork.buildService(LoginApi::class.java).refreshToken(refresh).enqueue(
            object : retrofit2.Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    when (response.code()) {
                        200, 201 -> {
                            val responseBody = response.body()
                            val gson = Gson()
                            if (responseBody != null) {
                                val json = gson.toJson(responseBody)
                                val tokens = gson.fromJson<LoginType>(
                                    json,
                                    object : TypeToken<LoginType>() {}.type
                                )

                                response(tokens)
                            }
                        }

                        400 -> {
                            // Handle unauthorized
                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("TagCodeCheck", "${t.message}")
                }
            })
    }

}