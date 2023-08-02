package uz.project.hunarbrand.main_fragment.detail.view_types


import com.google.gson.annotations.SerializedName

data class OrderLinkType(
    @SerializedName("pay_link")
    val payLink: String
)