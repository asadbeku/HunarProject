package uz.project.hunarbrand.profile.auth.signup.otpauth.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hunarbrend.signup.BearerTokenHolder
import com.example.hunarbrend.signup.otpauth.viewModel.OTPRepository

class OTPViewModel : ViewModel() {

    private val otpRepository = OTPRepository()

    private val _tokenLiveData = MutableLiveData<String>()
    private val _responseLiveData = MutableLiveData<Boolean>()
    private var _errorMessageSendLiveData = MutableLiveData<String?>()
    private var _errorMessageConformLiveData = MutableLiveData<String?>()
    private val _isLoadingSendData = MutableLiveData(false)
    private val _isLoadingConformData = MutableLiveData(false)

    val sendCodeErrorMessage: LiveData<String?>
        get() = _errorMessageSendLiveData

    val sendCodeIsLoading: LiveData<Boolean>
        get() = _isLoadingSendData

    val tokenLiveData: LiveData<String>
        get() = _tokenLiveData

    val response: LiveData<Boolean>
        get() = _responseLiveData

    fun getCode(phoneNumber: String) {
        _isLoadingSendData.postValue(true)
        Log.d(
            "navigateFragment",
            "(ViewModel) isLoading: ${sendCodeIsLoading.value.toString()}, token: ${_tokenLiveData.value.toString()}"
        )

        otpRepository.getCode(
            phoneNumber,
            { token ->
                _tokenLiveData.postValue(token)
                _isLoadingSendData.postValue(false)
                BearerTokenHolder.setToken(token)
                Log.d(
                    "navigateFragment",
                    "(ViewModel) isLoading: ${sendCodeIsLoading.value.toString()}, token: ${BearerTokenHolder.getToken()}"
                )
            },
            { errorMessage ->
                _errorMessageSendLiveData.value = errorMessage
                _isLoadingSendData.postValue(false)
                Log.d(
                    "TagCodeCheck",
                    "(ViewModel) errorMessage: ${_errorMessageSendLiveData.value.toString()}, token: ${_tokenLiveData.value.toString()}"
                )
            }
        )
    }

    fun resendCode() {

    }

    override fun onCleared() {

        _errorMessageSendLiveData.postValue(null)
        _errorMessageConformLiveData.postValue(null)

        super.onCleared()

    }
}