package uz.project.hunarbrand.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.project.hunarbrand.cart.view_types.CartType
import uz.project.hunarbrand.db.models.CartContract

@Dao
interface CartDao {

    @Query("SELECT * FROM ${CartContract.TABLE_NAME}")
    suspend fun getAllProductsFromCart(): List<CartType>

    @Query("SELECT * FROM ${CartContract.TABLE_NAME} WHERE ${CartContract.Columns.ID} = :productId")
    suspend fun getProductById(productId: Int): CartType

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductToCart(product: List<CartType>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeProductCount(product: List<CartType>)

    @Delete
    suspend fun deleteProductFromCart(product: List<CartType>)

}