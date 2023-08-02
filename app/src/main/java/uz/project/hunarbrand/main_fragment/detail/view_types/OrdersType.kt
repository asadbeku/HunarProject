package uz.project.hunarbrand.main_fragment.detail.view_types


import com.google.gson.annotations.SerializedName

data class OrdersType(
    @SerializedName("address")
    val address: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("pay_type")
    val payType: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("products")
    val products: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)