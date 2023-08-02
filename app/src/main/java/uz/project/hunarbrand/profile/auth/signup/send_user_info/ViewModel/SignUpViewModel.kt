package uz.project.hunarbrand.profile.auth.signup.send_user_info.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hunarbrend.signup.SendUserInfo.ViewModel.Repository
import okhttp3.MultipartBody

class SignUpViewModel : ViewModel() {

    val repository = Repository()

    fun setUserInfo(
        name: String,
        lastName: String,
        sex: String,
        direction: String,
        brandName: String,
        bio: String,
        password: String,
        conformPassword: String,
        avatar: MultipartBody.Part?,
        brandLogo: MultipartBody.Part?,
        callback: (Boolean) -> Unit
    ) {
        repository.patchUserInfo(
            name,
            lastName,
            direction,
            brandName,
            bio,
            sex,
            password,
            conformPassword,
            brandLogo,
            avatar
        ) { access ->
            if (access != "null") {
                Log.d("checkResponse", "Response: $access")
                callback(true)
            }else{
                callback(false)
            }
        }
    }


}