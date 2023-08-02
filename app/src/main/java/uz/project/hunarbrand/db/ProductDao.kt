package uz.project.hunarbrand.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.project.hunarbrand.db.models.ProductsContract
import uz.project.hunarbrand.main_fragment.main.types.ProductType

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<ProductType>)

    @Query("SELECT * FROM ${ProductsContract.TABLE_NAME}")
    suspend fun getAllProducts(): List<ProductType>

    @Query("SELECT * FROM ${ProductsContract.TABLE_NAME} WHERE ${ProductsContract.Columns.ID} = :productId")
    suspend fun getProductById(productId: Int): ProductType

}