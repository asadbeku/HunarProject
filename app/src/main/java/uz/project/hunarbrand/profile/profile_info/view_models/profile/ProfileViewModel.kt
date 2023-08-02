package uz.project.hunarbrand.profile.profile_info.view_models.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType

class ProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()
    private val _updatingState = MutableLiveData<Boolean>()
    val updatingState: LiveData<Boolean>
        get() = _updatingState

    fun getProfileInfo(requestProfileInfo: (response: ProfileInfoType) -> Unit) {
        _updatingState.postValue(true)
        viewModelScope.launch {
            repository.getProfileInfoFromDB {
                requestProfileInfo(it)
                _updatingState.postValue(false)
            }
        }
    }
}