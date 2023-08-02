package com.example.hunarbrend.signup.otpauth.viewModel

import android.util.Log
import uz.project.hunarbrand.profile.auth.signup.otpauth.di.conformcode.ConformCodeApi
import uz.project.hunarbrand.profile.auth.signup.otpauth.di.conformcode.ConformCodeNetwork
import uz.project.hunarbrand.profile.auth.signup.otpauth.di.sendcode.GetCodeApi
import com.example.hunarbrend.signup.otpauth.di.sendcode.SendCodeNetwork
import uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.requestdata.ConformCodeRequest
import uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.requestdata.GetCodeRequest
import uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.responsedata.error.ConformCodeErrorResponse
import uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.responsedata.error.SendCodeErrorDataResponse
import uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.responsedata.success.OTPResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OTPRepository {
    fun getCode(phoneNumber: String, token: (String) -> Unit, errorMessage: (String) -> Unit) {
        SendCodeNetwork.buildService(GetCodeApi::class.java)
            .sendCode(GetCodeRequest("998$phoneNumber")).enqueue(
                object : Callback<Any> {
                    override fun onResponse(
                        call: Call<Any>,
                        response: Response<Any>
                    ) {
                        when (response.code()) {
                            200, 201 -> {
                                val sendCodeResponse = otpResponse(response)
                                Log.d("TagCodeCheck", "${response.body()}")
                                token(sendCodeResponse?.access.toString())
                            }
                            400 -> {
                                val sendCodeErrorResponse = sendCodeErrorResponse(response)
                                errorMessage(sendCodeErrorResponse?.emailPhoneNumber?.message.toString())

                                onFailure(
                                    call,
                                    Throwable(sendCodeErrorResponse?.emailPhoneNumber?.message.toString())
                                )
                                Log.d(
                                    "TagCodeCheck",
                                    "onResponse (400): ${sendCodeErrorResponse?.emailPhoneNumber?.message.toString()}"
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

    fun conformCode(code: String, token: (String) -> Unit, errorMessage: (String) -> Unit) {
        ConformCodeNetwork.buildService(ConformCodeApi::class.java)
            .conformCode(ConformCodeRequest(code))
            .enqueue(
                object : Callback<Any> {
                    override fun onResponse(
                        call: Call<Any>, response: Response<Any>
                    ) {
                        when (response.code()) {
                            200, 201 -> {
                                val conformCodeResponse = otpResponse(response)
                                Log.d("repoTokenCount", "token: ${conformCodeResponse?.access.toString()}")
                                token(conformCodeResponse?.access.toString())
                            }
                            400 -> {
                                val errorBody = response.errorBody() ?: return
                                val type = object : TypeToken<ConformCodeErrorResponse>() {}.type
                                val errorResponse: ConformCodeErrorResponse? =
                                    Gson().fromJson(errorBody.charStream(), type)
                                val errorMessage1 =
                                    errorResponse?.message ?: "Unknown Error"

                                errorMessage(errorMessage1)
                                Log.d(
                                    "checkToken",
                                    "Error message: $errorMessage1"
                                )
                                token(null.toString())
                            }
                            401->{
                                Log.d("checkToken","Bearer tokens are failed")
                            }
                        }

//                        Log.d("repoTokenCount", "token: ${otpResponse(response)?.access.toString()}")
//                        token(otpResponse(response)?.access.toString())
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.d("TagCodeCheck", "$t")
                    }
                }
            )

    }


    fun otpResponse(response: Response<Any>): OTPResponse? {
        val conformCodeType = object : TypeToken<OTPResponse>() {}.type
        return Gson().fromJson(Gson().toJson(response.body()), conformCodeType)
    }

    fun sendCodeErrorResponse(response: Response<Any>): SendCodeErrorDataResponse? {
        val type = object : TypeToken<SendCodeErrorDataResponse>() {}.type
        return Gson().fromJson(response.errorBody()?.charStream(), type)
    }
}