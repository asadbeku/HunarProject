package uz.project.hunarbrand.cart.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import uz.project.hunarbrand.cart.view_types.ProductsInCart
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import uz.project.hunarbrand.utils.Bearer

class CartViewModel : ViewModel() {

    private val repository = CartRepository()

    private var _cartProducts = MutableLiveData<List<ProductsInCart>>()
    private var _totalPrice = MutableLiveData<Int>()
    private var _profileInfo = MutableLiveData<ProfileInfoType>()
    private var _errorMessage = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    val profileInfo: LiveData<ProfileInfoType>
        get() = _profileInfo

    val cartProducts: LiveData<List<ProductsInCart>>
        get() = _cartProducts
    val totalPrice: LiveData<Int>
        get() = _totalPrice

    fun getProfileInfo() {
        viewModelScope.launch {
            repository.getProfileInfo({
                _profileInfo.postValue(it)
            }, {
                _errorMessage.postValue(it)
            })
        }
    }

    fun deleteProductById(position: Int) {
        var list = _cartProducts.value?.toMutableList() ?: mutableListOf()
        viewModelScope.launch {
            repository.deleteProductById(list[position].id) {
                Log.d("checkProductInCart", "ViewModel: getCartProducts: $it")
                _cartProducts.postValue(it)
                getTotalPrice(it)
                list = it.toMutableList()
            }
        }
    }

    fun productCountPlus(position: Int) {
        val list = _cartProducts.value?.toMutableList().orEmpty()

        viewModelScope.launch {
            repository.incrementProductCount(list[position].id) {
                _cartProducts.postValue(it)
                getTotalPrice(it)
            }
        }

        _cartProducts.postValue(list)
    }

    fun productCountMinus(id: Int) {
        val list = _cartProducts.value?.toMutableList().orEmpty()

        viewModelScope.launch {

            repository.decrementProductCount(list[id].id) {
                _cartProducts.postValue(it)
                getTotalPrice(it)
            }
        }


    }

    private fun getTotalPrice(list: List<ProductsInCart>) {
        var total = 0

        for (product in list) {
            Log.d(
                "checkTotalPrice",
                "Total(${total}) =Product count: ${product.productCount}*${
                    product.price.toDouble().toInt()
                }"
            )
            total += product.productCount * product.price.toDouble().toInt()
        }

        _totalPrice.postValue(total)
    }

    fun getCartProducts() {
        viewModelScope.launch {
            repository.getCartProducts() {
                Log.d("checkProductInCart", "ViewModel: getCartProducts: $it")
                _cartProducts.postValue(it)
                getTotalPrice(it)
            }
        }


    }

    fun makeOrder(json: RequestBody, context: Context, responseLink: (link: String) -> Unit) {
        repository.buyProducts(json) { order ->
            Log.d("TagCodeCheck", "buyProduct: $order")
            createLink(order.id, order.amount.toDouble(), context) { link ->
                responseLink(link)
            }
        }
    }

    private fun createLink(
        id: Int,
        amount: Double,
        context: Context,
        responseLink: (link: String) -> Unit
    ) {

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

            Bearer.clearProductsInCart(context)
        }
    }
}