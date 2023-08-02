package uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.responsedata.error

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

@Json(name = "email_phone_number")
data class SendCodeErrorResponse(
    @SerializedName("success") val success: String? = null,
    @SerializedName("message") val message: String? = null
)