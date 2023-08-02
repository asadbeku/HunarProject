package uz.project.hunarbrand.profile.auth.login.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.DecodedJWT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.profile.auth.login.view_types.Jwt
import uz.project.hunarbrand.profile.profile_info.view_models.profile.ProfileRepository
import uz.project.hunarbrand.profile.profile_info.view_types.LoginType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import uz.project.hunarbrand.utils.Bearer


class LoginViewModel : ViewModel() {
    private val jwtDao = Database.instance.jwtDao()
    private val profileDao = Database.instance.profileDao()
    private val repository = LoginRepository()
    private val profileRepository = ProfileRepository()

    private val _login = MutableLiveData<LoginType>()
    private val _updatingState = MutableLiveData<Boolean>()
    val login: LiveData<LoginType> get() = _login
    val updatingState: LiveData<Boolean> get() = _updatingState

    fun test() {
        viewModelScope.launch {
            setUserInfoToDB(
                ProfileInfoType(
                    1,
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "null",
                    "null",
                )
            )
        }

    }

    fun login(phoneNumber: String, password: String, error: (String) -> Unit) {
        _updatingState.value = true

        repository.login("998$phoneNumber", password, { result ->

            val expired = JWT.decode(result.access).expiresAt.time
            val userId = JWT.decode(result.access).claims["user_id"]?.asInt() ?: 0
            if (System.currentTimeMillis() < expired) {
                viewModelScope.launch {
                    jwtDao.insertJwt(
                        Jwt(userId, result.access, result.refresh, expired, expired)
                    )
                }

                getUserInfoFromNetwork(userId) {
                    setUserInfoToDB(it)

                    _login.value = result
                    _updatingState.value = false
                }
            }
        }, { errorMessage ->
            error(errorMessage)
            _updatingState.value = false
        })
    }

    private fun getUserInfoFromNetwork(userId: Int, response: (response: ProfileInfoType) -> Unit) {
        profileRepository.getProfileInfoFromNetwork(userId) { userInfo ->
            Log.d("checkProfileInfo", "Profile info from network: $userInfo")
            response(userInfo)
        }
    }

    private fun setUserInfoToDB(userInfo: ProfileInfoType) {
        GlobalScope.launch {
            profileRepository.setProfileInfoToDB(userInfo)
        }
    }

    fun refreshToken(context: Context) {
        Bearer.getRefreshToken(context)?.let { refreshToken ->
            repository.refreshToken(refreshToken) { result ->
                Bearer.saveAccessToken(context, result.access)
                Bearer.saveRefreshToken(context, result.refresh)
            }
        }
    }
}
