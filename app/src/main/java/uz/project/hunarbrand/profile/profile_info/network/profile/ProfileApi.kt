package uz.project.hunarbrand.profile.profile_info.network.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType

interface ProfileApi {

    @GET("profile/{id}/")
    fun getProfileInfo(
        @Path("id") id: Int
    ): Call<ProfileInfoType>
}