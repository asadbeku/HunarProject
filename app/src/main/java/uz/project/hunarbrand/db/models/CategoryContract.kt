package uz.project.hunarbrand.db.models

object CategoryContract {
    const val TABLE_NAME = "category"

    object Columns {
        const val ID = "id"
        const val name_uz = "name_uz"
        const val name_ru = "name_ru"
        const val name_en = "name_en"
        const val image = "image"
        const val about_en = "about_en"
        const val about_ru = "about_ru"
        const val about_uz = "about_uz"
    }
}