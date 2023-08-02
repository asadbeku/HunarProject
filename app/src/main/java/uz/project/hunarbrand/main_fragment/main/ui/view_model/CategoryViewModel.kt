package uz.project.hunarbrand.main_fragment.main.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.project.hunarbrand.main_fragment.main.types.CategoryResList

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()

    private var _categoryList = MutableLiveData<List<CategoryResList>>()

    val categoryList: LiveData<List<CategoryResList>>
        get() = _categoryList

    fun getCategory() {
        val convertedList = mutableListOf<CategoryResList>()
        viewModelScope.launch {
            repository.getAllCategory { list ->
                Log.d("checkCategory", "View model: $list")
                for (item in list) {
                    convertedList += CategoryResList(item.name_uz.toString(), item.image.toString())
                }
                _categoryList.postValue(convertedList)
            }
        }
    }

}