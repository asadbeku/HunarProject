package uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.responsedata.error

import com.google.gson.annotations.SerializedName

data class ConformCodeErrorResponse(
    @SerializedName("message") val message: String
)