package uz.project.hunarbrand.main_fragment.main.types

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.project.hunarbrand.db.models.CategoryContract
import uz.project.hunarbrand.db.models.ProductsContract

@Entity(tableName = CategoryContract.TABLE_NAME)
data class CategoryType(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CategoryContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = CategoryContract.Columns.name_uz)
    val name_uz: String,
    @ColumnInfo(name = CategoryContract.Columns.name_ru)
    val name_ru: String?,
    @ColumnInfo(name = CategoryContract.Columns.name_en)
    val name_en: String?,
    @ColumnInfo(name = CategoryContract.Columns.image)
    val image: String?,
    @ColumnInfo(name = CategoryContract.Columns.about_en)
    val about_en: String?,
    @ColumnInfo(name = CategoryContract.Columns.about_ru)
    val about_ru: String?,
    @ColumnInfo(name = CategoryContract.Columns.about_uz)
    val about_uz: String?
)