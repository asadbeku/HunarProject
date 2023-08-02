package uz.project.hunarbrand.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.project.hunarbrand.db.models.ProfileInfoContract
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileInfo(profileInfo: ProfileInfoType)

    @Query("SELECT * FROM ${ProfileInfoContract.TABLE_NAME}")
    suspend fun getProfileInfo(): ProfileInfoType

}