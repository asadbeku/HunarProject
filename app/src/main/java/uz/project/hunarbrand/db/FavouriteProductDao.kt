package uz.project.hunarbrand.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.project.hunarbrand.db.models.FavouriteProductContract
import uz.project.hunarbrand.main_fragment.detail.view_types.LikedProduct

@Dao
interface FavouriteProductDao {

    @Query("SELECT * FROM ${FavouriteProductContract.TABLE_NAME}")
    suspend fun getAllFavouriteProducts(): List<LikedProduct>

    @Query("SELECT * FROM ${FavouriteProductContract.TABLE_NAME} WHERE ${FavouriteProductContract.Columns.ID} = :productId")
    suspend fun getFavouriteProductById(productId: Int): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteProduct(id: LikedProduct)

    @Query("DELETE FROM ${FavouriteProductContract.TABLE_NAME} WHERE ${FavouriteProductContract.Columns.ID} = :id")
    suspend fun removeFavouriteProduct(id: Int)

}