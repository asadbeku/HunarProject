package uz.project.hunarbrand.profile.profile_info.view_models.profile

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.profile.profile_info.network.profile.ProfileApi
import uz.project.hunarbrand.profile.profile_info.network.profile.ProfileNetwork
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileRepository {
    private val profileDao = Database.instance.profileDao()

    suspend fun getProfileInfoFromDB(response: (response: ProfileInfoType) -> Unit) {
        try {
            val profileInfo = profileDao.getProfileInfo()
            response(profileInfo)
        } catch (t: Throwable) {
            Log.d("DBError", "getProfileInfoFromDB: ${t.message}")
        }
    }

    suspend fun setProfileInfoToDB(profileInfo: ProfileInfoType) {
        try {
            profileDao.insertProfileInfo(profileInfo)
        } catch (t: Throwable) {
            Log.d("DBError", "setProfileInfoToDB: ${t.message}")
        }
    }

    fun getProfileInfoFromNetwork(id: Int, response: (response: ProfileInfoType) -> Unit) {
        ProfileNetwork.buildService(ProfileApi::class.java).getProfileInfo(id)
            .enqueue(object : Callback<ProfileInfoType> {
                override fun onResponse(
                    call: Call<ProfileInfoType>,
                    response: Response<ProfileInfoType>
                ) {
                    when (response.code()) {
                        200, 201 -> {
                            response(response.body()!!)
                        }

                        400 -> {
                            Log.e("NetworkException", "Bad Request")
                        }

                        404 -> {
                            Log.e("NetworkException", "User not Found")
                        }

                        else -> {
                            Log.e("NetworkException", response.message())
                        }
                    }
                }

                override fun onFailure(call: Call<ProfileInfoType>, t: Throwable) {
                    Log.e("NetworkException", t.message.toString())
                }
            })
    }
}