package uz.project.hunarbrand.profile.auth.signup.otpauth.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hunarbrend.signup.BearerTokenHolder
import com.example.hunarbrend.signup.otpauth.viewModel.OTPRepository

class ConformViewModel: ViewModel() {

    private val otpRepository = OTPRepository()

    private val _tokenLiveData = MutableLiveData<String>()
    private val _responseLiveData = MutableLiveData<Boolean>()
    private var _errorMessageConformLiveData = MutableLiveData<String?>()
    private val _isLoadingSendData = MutableLiveData(false)
    private val _isLoadingConformData = MutableLiveData(false)

    val conformCodeErrorMessage: LiveData<String?>
        get() = _errorMessageConformLiveData

    val sendCodeIsLoading: LiveData<Boolean>
        get() = _isLoadingSendData

    val conformCodeIsLoading: LiveData<Boolean>
        get() = _isLoadingConformData

    val tokenLiveData: LiveData<String>
        get() = _tokenLiveData

    val response: LiveData<Boolean>
        get() = _responseLiveData

    fun conformCode(code: String, response: (Boolean) -> Unit) {
        _isLoadingConformData.postValue(true)
        Log.d(
            "checkToken",
            "TokenHolder: ${BearerTokenHolder.getToken()}, LiveData: ${_tokenLiveData.value.toString()}"
        )
        otpRepository.conformCode(
            code,
            { responseToken ->
                _tokenLiveData.postValue(responseToken)
                _isLoadingSendData.postValue(false)
                Log.d(
                    "checkToken",
                    "responseToken: $responseToken, LiveData: ${_tokenLiveData.value.toString()}"
                )
                if (responseToken != "null") {
                    BearerTokenHolder.setToken(responseToken)
                    response(true)
                    Log.d("checkCountResponse", "response: ${_responseLiveData.value.toString()}")
                } else {
                    response(false)
                    Log.d("checkCountResponse", "response: ${_responseLiveData.value.toString()}")
                }
            },
            { message ->
                _errorMessageConformLiveData.postValue(message)
                Log.d("checkToken", "message: $message")
                _isLoadingConformData.postValue(false)
//                response(false)
                Log.d("checkCountResponse", "response: ${_responseLiveData.value.toString()}")
            }
        )
    }

}