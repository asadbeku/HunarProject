package uz.project.hunarbrand.profile.profile_info.view_models.my_products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class MyBrandViewModel : ViewModel() {

    private val repository = MyProductsRepository()

    private val _myBrandProducts = MutableLiveData<List<ProductType>>()
    val myBrandProducts: LiveData<List<ProductType>>
        get() = _myBrandProducts

    fun getBrandProducts(id: Int) {
        repository.getProducts(id) { products ->
            val filteredProducts = products.filter { it.e_status }
            _myBrandProducts.postValue(filteredProducts)
            Log.d("checkProduct", "DetailViewModel: Products $filteredProducts")
        }
    }

}