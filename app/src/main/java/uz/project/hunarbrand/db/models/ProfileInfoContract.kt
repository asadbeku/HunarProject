package uz.project.hunarbrand.db.models

object ProfileInfoContract {

    const val TABLE_NAME = "profile_info"

    object Columns{
        const val ID = "id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val USER_ROLE = "user_roles"
        const val AUTH_TYPE = "auth_type"
        const val AUTH_STATUS = "auth_status"
        const val SEX = "sex"
        const val PHONE_NUMBER = "phone_number"
        const val BIO_UZ = "bio_uz"
        const val BIO_RU = "bio_ru"
        const val BIO_EN = "bio_en"
        const val DIRECTION_UZ = "direction_uz"
        const val DIRECTION_RU = "direction_ru"
        const val BRAND_NAME = "brand_name"
        const val ADDRESS = "address"
        const val AVATAR = "avatar"
        const val LOGO = "logo"
    }


}