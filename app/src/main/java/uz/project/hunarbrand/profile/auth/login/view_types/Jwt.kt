package uz.project.hunarbrand.profile.auth.login.view_types

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jwt")
data class Jwt(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "access")
    val access: String,
    @ColumnInfo(name = "refresh")
    val refresh: String,
    @ColumnInfo(name = "access_expired_at")
    val accessExpiredAt: Long,
    @ColumnInfo(name = "refresh_expired_at")
    val refreshExpiredAt: Long


)
