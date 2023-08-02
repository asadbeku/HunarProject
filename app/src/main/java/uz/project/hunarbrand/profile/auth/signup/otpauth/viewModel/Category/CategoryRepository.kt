package uz.project.hunarbrand.profile.auth.signup.otpauth.viewModel.Category

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.main_fragment.main.types.CategoryType
import uz.project.hunarbrand.main_fragment.main.ui.network.GetAllCategoryApi
import uz.project.hunarbrand.main_fragment.main.ui.network.GetAllCategoryNetwork

class CategoryRepository {

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
}