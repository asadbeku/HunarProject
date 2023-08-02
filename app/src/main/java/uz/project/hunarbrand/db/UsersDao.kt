package uz.project.hunarbrand.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.project.hunarbrand.db.models.UsersContract
import uz.project.hunarbrand.search.type_view.UserType

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserType>)

    @Query("SELECT * FROM ${UsersContract.TABLE_NAME}")
    suspend fun getSearchItems(): List<UserType>

}