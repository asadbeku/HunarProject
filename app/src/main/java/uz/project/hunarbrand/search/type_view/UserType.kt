package uz.project.hunarbrand.search.type_view

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.project.hunarbrand.db.models.UsersContract

@Entity(tableName = "users")
data class UserType(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UsersContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = UsersContract.Columns.FIRST_NAME)
    val first_name: String?,
    @ColumnInfo(name = UsersContract.Columns.LAST_NAME)
    val last_name: String?,
    @ColumnInfo(name = UsersContract.Columns.USER_ROLES)
    val user_roles: String?,
    @ColumnInfo(name = UsersContract.Columns.AUTH_TYPE)
    val auth_type: String?,
    @ColumnInfo(name = UsersContract.Columns.AUTH_STATUS)
    val auth_status: String?,
    @ColumnInfo(name = UsersContract.Columns.SEX)
    val sex: String?,
    @ColumnInfo(name = UsersContract.Columns.PHONE_NUMBER)
    val phone_number: String?,
    @ColumnInfo(name = UsersContract.Columns.BIO_UZ)
    val bio_uz: String?,
    @ColumnInfo(name = UsersContract.Columns.BIO_RU)
    val bio_ru: String?,
    @ColumnInfo(name = UsersContract.Columns.BIO_EN)
    val bio_en: String?,
    @ColumnInfo(name = UsersContract.Columns.DIRECTION_UZ)
    val direction_uz: String?,
    @ColumnInfo(name = UsersContract.Columns.DIRECTION_RU)
    val direction_ru: String?,
    @ColumnInfo(name = UsersContract.Columns.BRAND_NAME)
    val brand_name: String?,
    @ColumnInfo(name = UsersContract.Columns.ADDRESS)
    val address: String?,
    @ColumnInfo(name = UsersContract.Columns.AVATAR)
    val avatar: String?,
    @ColumnInfo(name = UsersContract.Columns.LOGO)
    val logo: String?
)