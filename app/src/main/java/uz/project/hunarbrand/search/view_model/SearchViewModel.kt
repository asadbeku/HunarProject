package uz.project.hunarbrand.search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.project.hunarbrand.search.type_view.UserType

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()
    private var _categoryLiveData = MutableLiveData<List<UserType>>()
    private var _findCategoryLiveData = MutableLiveData<List<UserType>>()

    val usersList: LiveData<List<UserType>>
        get() = _categoryLiveData

    val findCategoryList: LiveData<List<UserType>>
        get() = _findCategoryLiveData

    fun getUsers() {
        viewModelScope.launch {
            val list = mutableListOf<UserType>()

            repository.getCategoryList() {
                for (item in it) {
                    if (item.brand_name != null) {
                        list += item
                    }
                }
                _categoryLiveData.postValue(list)
            }
        }
    }

    fun findUsers(text: String) {
        viewModelScope.launch {
            _findCategoryLiveData.postValue(repository.findCategory(text))
        }
    }
}