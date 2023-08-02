package uz.project.hunarbrand.profile.profile_info.view_types


import com.google.gson.annotations.SerializedName
import uz.project.hunarbrand.main_fragment.main.types.ProductType

data class MyProducts(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("page_count")
    val pageCount: Int,
    @SerializedName("previous")
    val previous: Int?,
    @SerializedName("results")
    val results: List<ProductType>
)