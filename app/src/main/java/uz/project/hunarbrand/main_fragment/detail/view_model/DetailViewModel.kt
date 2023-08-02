package uz.project.hunarbrand.main_fragment.detail.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType

class DetailViewModel : ViewModel() {

    private val repository = DetailRepository()

    private var _detailProductLiveData = MutableLiveData<ProductType>()
    private var _isLoading = MutableLiveData<Boolean>()
    private var _isLiked = MutableLiveData<Boolean>()
    private var _errorMessage = MutableLiveData<String>()
    private var _profileInfo = MutableLiveData<ProfileInfoType>()

    val profileInfo: LiveData<ProfileInfoType>
        get() = _profileInfo

    val errorMessage: LiveData<String>
        get() = _errorMessage
    val isLiked: LiveData<Boolean>
        get() = _isLiked

    val detailProduct: LiveData<ProductType>
        get() = _detailProductLiveData

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProfileInfo() {
        viewModelScope.launch {
            repository.getProfileInfo({
                _profileInfo.postValue(it)
            }, {
                _errorMessage.postValue(it)
            })
        }
    }

    fun getProductInfo(id: Int) {
        _isLoading.postValue(true)
        repository.getProductInfo(id) {
            _detailProductLiveData.postValue(it)
            _isLoading.postValue(false)
        }
    }

    fun addProductToCart(id: Int) {
        viewModelScope.launch {
            repository.addToCart(id){
                Log.d("TagCodeCheck", "addProductToCart: $it")
            }
        }
    }

    fun buyProduct(json: RequestBody, responseLink: (link: String) -> Unit) {
        repository.buyProducts(json) { order ->
            Log.d("TagCodeCheck", "buyProduct: $order")
            createLink(order.id, order.amount.toDouble()) { link ->
                responseLink(link)
            }
        }
    }

    private fun createLink(id: Int, amount: Double, responseLink: (link: String) -> Unit) {

        val data = mapOf(
            "order_id" to id,
            "amount" to amount
        )

        val gson = Gson()
        val json = gson.toJson(data)

        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = RequestBody.create(mediaType, json)

        repository.createLink(requestBody) {
            Log.d("TagCodeCheck", "createLink: $it")
            responseLink(it.payLink)
        }
    }

    fun isLiked(id: Int) {
        viewModelScope.launch {
            repository.isLiked(id) {
                _isLiked.postValue(it)
            }
        }
    }

    fun likeProduct(id: Int) {
        viewModelScope.launch {
            repository.likeProduct(id) { isLiked ->
                Log.d("LikedProduct", "likeProduct: $isLiked")
                _isLiked.postValue(isLiked)
            }
        }
    }


}