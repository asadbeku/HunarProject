package uz.project.hunarbrand.profile.profile_info.view_models.my_products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class MyExclusiveViewModel : ViewModel() {

    private val repository = MyProductsRepository()

    private var _myExclusiveProducts = MutableLiveData<List<ProductType>>()

    val myExclusiveProducts: LiveData<List<ProductType>>
        get() = _myExclusiveProducts

    fun getExclusiveProducts(id: Int) {
        repository.getProducts(id) { products ->
            val filteredProducts = products.filter { it.e_status }
            _myExclusiveProducts.postValue(filteredProducts)
            Log.d("checkProduct", "DetailViewModel: Products $filteredProducts")
        }
    }
}