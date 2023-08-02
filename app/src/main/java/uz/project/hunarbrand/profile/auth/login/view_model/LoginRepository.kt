package uz.project.hunarbrand.profile.auth.login.view_model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Response
import uz.project.hunarbrand.profile.auth.login.network.LoginApi
import uz.project.hunarbrand.profile.auth.login.network.LoginNetwork
import uz.project.hunarbrand.profile.profile_info.view_types.LoginType

class LoginRepository {

    fun login(phoneNumber: String, password: String, response: (LoginType) -> Unit, error: (error: String)-> Unit) {
        LoginNetwork.buildService(LoginApi::class.java).login(phoneNumber, password).enqueue(
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
                            error("Login yoki parol xato")
                        }
                        401 -> {
                            error("Bunday foydalanuvchi mavjud emas")
                        }
                        else -> {
                            error("Boshqa xatolik yuz berdi")
                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    error("Internet bilan bog'lanishda xatolik")
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
                    Log.d("checkRefreshTokenFail", "${t.message}")
                }
            })
    }


}