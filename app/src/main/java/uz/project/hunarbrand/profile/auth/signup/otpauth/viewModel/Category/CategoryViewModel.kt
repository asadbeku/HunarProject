package com.example.hunarbrend.signup.otpauth.viewModel.Category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.project.hunarbrand.profile.auth.signup.otpauth.viewModel.Category.CategoryRepository

class CategoryViewModel : ViewModel() {
    private val repository = CategoryRepository()

    private var _categoryListLiveData = MutableLiveData<List<String>>()
    val categoryListLiveData: LiveData<List<String>>
        get() = _categoryListLiveData

    fun getCategory() {
        repository.getCategories { categoryList ->
            Log.d("checkCategoryFragment", "Fragment || categoryDropDown: $categoryList")
            var list = mutableListOf<String>()

            for (category in categoryList) {
                if (category.name_uz != null) {
                    list.add(category.name_uz)
                }
            }

            _categoryListLiveData.postValue(list)
        }


    }
}