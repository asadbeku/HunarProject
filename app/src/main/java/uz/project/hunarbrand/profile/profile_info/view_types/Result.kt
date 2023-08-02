package uz.project.hunarbrand.profile.profile_info.view_types


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("compound_en")
    val compoundEn: Any?,
    @SerializedName("compound_ru")
    val compoundRu: Any?,
    @SerializedName("compound_uz")
    val compoundUz: String,
    @SerializedName("e_status")
    val eStatus: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_2")
    val image2: String,
    @SerializedName("image_3")
    val image3: String,
    @SerializedName("image_4")
    val image4: String,
    @SerializedName("main_image")
    val mainImage: String,
    @SerializedName("massa")
    val massa: String,
    @SerializedName("name_en")
    val nameEn: Any?,
    @SerializedName("name_ru")
    val nameRu: Any?,
    @SerializedName("name_uz")
    val nameUz: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("soha_en")
    val sohaEn: Any?,
    @SerializedName("soha_ru")
    val sohaRu: Any?,
    @SerializedName("soha_uz")
    val sohaUz: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("user")
    val user: Int,
    @SerializedName("weight")
    val weight: Int
)