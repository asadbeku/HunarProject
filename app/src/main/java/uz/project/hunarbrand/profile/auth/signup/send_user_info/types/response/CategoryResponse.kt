package uz.project.hunarbrand.profile.auth.signup.send_user_info.types.response


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("about_en")
    val aboutEn: String,
    @SerializedName("about_ru")
    val aboutRu: String,
    @SerializedName("about_uz")
    val aboutUz: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("name_ru")
    val nameRu: String,
    @SerializedName("name_uz")
    val nameUz: String
)