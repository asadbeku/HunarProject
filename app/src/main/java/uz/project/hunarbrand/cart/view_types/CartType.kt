package uz.project.hunarbrand.cart.view_types

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.project.hunarbrand.db.models.CartContract

@Entity(tableName = CartContract.TABLE_NAME)
data class CartType(
    @PrimaryKey
    @ColumnInfo(name = CartContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = CartContract.Columns.COUNT)
    val count: Int
)