package uz.project.hunarbrand.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.project.hunarbrand.db.models.CategoryContract
import uz.project.hunarbrand.db.models.ProductsContract
import uz.project.hunarbrand.main_fragment.main.types.CategoryType

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: List<CategoryType>)

    @Query("SELECT * FROM ${CategoryContract.TABLE_NAME}")
    suspend fun getAllCategories(): List<CategoryType>

}