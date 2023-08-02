package uz.project.hunarbrand.liked.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import uz.project.hunarbrand.liked.view_types.BuyProductType
import uz.project.hunarbrand.main_fragment.detail.view_model.DetailRepository
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType

class LikedViewModel : ViewModel() {
    private val repository = LikedRepository()
    private val detailRepository = DetailRepository()
    private var _likedProducts = MutableLiveData<List<ProductType>>()
    val likedProducts: MutableLiveData<List<ProductType>>
        get() = _likedProducts

    fun getLikedProducts() {
        viewModelScope.launch {
            repository.getLikedProducts() {
                _likedProducts.postValue(it)
            }
        }
    }

    fun generateProductToBuy(id: Int, response: (product: BuyProductType) -> Unit) {
        viewModelScope.launch {

            val list = _likedProducts.value?.toList().orEmpty()

            repository.getUserInfo {
                if (it == null) {
                    Log.d("TagCodeCheck", "generateProductToBuy: Not Authorised")
                    response(
                        BuyProductType(
                            "",
                            "",
                            "",
                            listOf(list[id].id.toString()),
                            list[id].price
                        )
                    )
                    return@getUserInfo
                } else {
                    Log.d("TagCodeCheck", "generateProductToBuy: Authorised")

                    response(
                        BuyProductType(
                            it.firstName + " " + it.lastName,
                            it.phoneNumber.substring(3),
                            it.address,
                            listOf(list[id].id.toString()),
                            list[id].price
                        )
                    )
                }

            }


        }
    }

    fun addProductToCart(id: Int, response: (response: String) -> Unit) {
        val list = _likedProducts.value?.toList().orEmpty()

        viewModelScope.launch {
            detailRepository.addToCart(list[id].id) {
                if (it) {
                    response("Maxsulot savatga qo'shildi")
                } else {
                    response("Maxsulot savatga qo'shilmadi")
                }
            }
        }
    }

    fun removeFromFavourite(id: Int, responseLink: (message: String) -> Unit) {
        val list = _likedProducts.value?.toList().orEmpty()
        viewModelScope.launch {
            detailRepository.likeProduct(id) {
                if (!it) {
                    val newList = list.toMutableList()
                    newList.removeAt(id)
                    responseLink("Maxsulot o'chirildi")
                    _likedProducts.postValue(newList)
                } else {
                    responseLink("Maxsulotni o'chirishda xatolik")
                }
            }
        }

    }

    fun buyProduct(json: RequestBody, responseLink: (link: String) -> Unit) {
        detailRepository.buyProducts(json) { order ->
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

        detailRepository.createLink(requestBody) {
            Log.d("TagCodeCheck", "createLink: $it")
            responseLink(it.payLink)
        }
    }

}