package uz.project.hunarbrand.main_fragment.main.products.brands.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.project.hunarbrand.main_fragment.main.products.view_model.GetProductRepository
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class BrandViewModel : ViewModel() {
    private val repository = GetProductRepository()

    private var productList = MutableLiveData<List<ProductType>>()
    val products: LiveData<List<ProductType>>
        get() = productList

    fun getProducts() {
        viewModelScope.launch {
            repository.getAllProducts() { list ->
                val filteredList: MutableList<ProductType> = mutableListOf()
                for (item in list) {
                    filteredList.add(item)
                }

                productList.postValue(filteredList)
            }
        }
    }

    fun likeProduct(id: Int) {
        viewModelScope.launch {
            repository.likeProduct(id)
        }
    }
}