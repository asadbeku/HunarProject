package uz.project.hunarbrand.main_app.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    fun userIsAuth(): Boolean {
        return runBlocking {
            repository.userIsAuth()
        }
    }
}