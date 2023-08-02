package uz.project.hunarbrand.main_fragment.detail.view_types

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.project.hunarbrand.db.models.FavouriteProductContract

@Entity(tableName = FavouriteProductContract.TABLE_NAME)
data class LikedProduct(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FavouriteProductContract.Columns.ID)
    val id: Int
)