package uz.project.hunarbrand.profile.profile_info.view_types


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.project.hunarbrand.db.models.ProfileInfoContract

@Entity(tableName = ProfileInfoContract.TABLE_NAME)
data class ProfileInfoType(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProfileInfoContract.Columns.ID)
    @SerializedName(ProfileInfoContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = ProfileInfoContract.Columns.FIRST_NAME)
    @SerializedName(ProfileInfoContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.LAST_NAME)
    @SerializedName(ProfileInfoContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.USER_ROLE)
    @SerializedName(ProfileInfoContract.Columns.USER_ROLE)
    val userRoles: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.AUTH_TYPE)
    @SerializedName(ProfileInfoContract.Columns.AUTH_TYPE)
    val authType: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.AUTH_STATUS)
    @SerializedName(ProfileInfoContract.Columns.AUTH_STATUS)
    val authStatus: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.SEX)
    @SerializedName(ProfileInfoContract.Columns.SEX)
    val sex: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.PHONE_NUMBER)
    @SerializedName(ProfileInfoContract.Columns.PHONE_NUMBER)
    val phoneNumber: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.BIO_UZ)
    @SerializedName(ProfileInfoContract.Columns.BIO_UZ)
    val bioUz: String?,
    @ColumnInfo(name = ProfileInfoContract.Columns.BIO_RU)
    @SerializedName(ProfileInfoContract.Columns.BIO_RU)
    val bioRu: String?,
    @ColumnInfo(name = ProfileInfoContract.Columns.BIO_EN)
    @SerializedName(ProfileInfoContract.Columns.BIO_EN)
    val bioEn: String?,
    @ColumnInfo(name = ProfileInfoContract.Columns.DIRECTION_UZ)
    @SerializedName(ProfileInfoContract.Columns.DIRECTION_UZ)
    val directionUz: String,
    @ColumnInfo(name = ProfileInfoContract.Columns.DIRECTION_RU)
    @SerializedName(ProfileInfoContract.Columns.DIRECTION_RU)
    val directionRu: String?,
    @ColumnInfo(name = ProfileInfoContract.Columns.BRAND_NAME)
    @SerializedName(ProfileInfoContract.Columns.BRAND_NAME)
    val brandName: String?,
    @ColumnInfo(name = ProfileInfoContract.Columns.ADDRESS)
    @SerializedName(ProfileInfoContract.Columns.ADDRESS)
    val address: String?,
    @ColumnInfo(name = ProfileInfoContract.Columns.AVATAR)
    @SerializedName(ProfileInfoContract.Columns.AVATAR)
    val avatar: String?,
    @ColumnInfo(name = ProfileInfoContract.Columns.LOGO)
    @SerializedName(ProfileInfoContract.Columns.LOGO)
    val logo: String?,
)