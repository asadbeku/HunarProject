package uz.project.hunarbrand.profile.auth.signup.otpauth.request_response_types.responsedata.success

data class SendCodeResponse(
    val guid: String,
    val auth_type: String,
    val auth_status: String,
    val access: String,
    val refresh: String
)