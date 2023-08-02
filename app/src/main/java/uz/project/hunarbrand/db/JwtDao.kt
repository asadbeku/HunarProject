package uz.project.hunarbrand.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.project.hunarbrand.db.models.JwtContract
import uz.project.hunarbrand.profile.auth.login.view_types.Jwt

@Dao
interface JwtDao {

    @Query("SELECT * FROM ${JwtContract.TABLE_NAME}")
    suspend fun getJwt(): Jwt

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJwt(jwt: Jwt)

}