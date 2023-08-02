package uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.responsedata.error

import com.google.gson.annotations.SerializedName

data class SendCodeErrorDataResponse(
    @SerializedName("email_phone_number") var emailPhoneNumber: SendCodeErrorResponse? = SendCodeErrorResponse()
)