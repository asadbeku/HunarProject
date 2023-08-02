package uz.project.hunarbrand.main_fragment.main.types

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.project.hunarbrand.db.models.ProductsContract

@Entity(
    tableName = ProductsContract.TABLE_NAME
)
data class ProductType(
    @PrimaryKey
    @ColumnInfo(name = ProductsContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = ProductsContract.Columns.soha_uz)
    val soha_uz: String?,
    @ColumnInfo(name = ProductsContract.Columns.soha_ru)
    val soha_ru: String?,
    @ColumnInfo(name = ProductsContract.Columns.soha_en)
    val soha_en: String?,
    @ColumnInfo(name = ProductsContract.Columns.user)
    val user: Int,
    @ColumnInfo(name = ProductsContract.Columns.name_uz)
    val name_uz: String?,
    @ColumnInfo(name = ProductsContract.Columns.name_ru)
    val name_ru: String?,
    @ColumnInfo(name = ProductsContract.Columns.name_en)
    val name_en: String?,
    @ColumnInfo(name = ProductsContract.Columns.weight)
    val weight: Int,
    @ColumnInfo(name = ProductsContract.Columns.massa)
    val massa: String,
    @ColumnInfo(name = ProductsContract.Columns.compound_uz)
    val compound_uz: String?,
    @ColumnInfo(name = ProductsContract.Columns.compound_ru)
    val compound_ru: String?,
    @ColumnInfo(name = ProductsContract.Columns.compound_en)
    val compound_en: String?,
    @ColumnInfo(name = ProductsContract.Columns.price)
    val price: String,
    @ColumnInfo(name = ProductsContract.Columns.status)
    val status: Boolean,
    @ColumnInfo(name = ProductsContract.Columns.e_status)
    val e_status: Boolean,
    @ColumnInfo(name = ProductsContract.Columns.main_image)
    val main_image: String?,
    @ColumnInfo(name = ProductsContract.Columns.image_2)
    val image_2: String?,
    @ColumnInfo(name = ProductsContract.Columns.image_3)
    val image_3: String?,
    @ColumnInfo(name = ProductsContract.Columns.image_4)
    val image_4: String?
)