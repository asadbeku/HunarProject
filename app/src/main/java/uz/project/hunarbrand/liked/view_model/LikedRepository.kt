package uz.project.hunarbrand.liked.view_model

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType

class LikedRepository {

    private val favouriteDao = Database.instance.favouriteProductDao()
    private val productDao = Database.instance.productDao()
    private val profileDao = Database.instance.profileDao()

    suspend fun getLikedProducts(response: (response: List<ProductType>) -> Unit) {
        withContext(Dispatchers.Main) {
            val likedProductsIds = favouriteDao.getAllFavouriteProducts()
            val likedProductsList: MutableList<ProductType> = mutableListOf()

            for (likedProduct in likedProductsIds) {
                likedProductsList += productDao.getProductById(likedProduct.id)
            }

            response(likedProductsList)
        }


    }

    suspend fun getUserInfo(responseUserInfo: (userInfo: ProfileInfoType?) -> Unit) {
        withContext(Dispatchers.Main) {
            try {
                val userInfo = profileDao.getProfileInfo()
                responseUserInfo(userInfo)
            } catch (e: Exception) {
                Log.d("DBException", "getUserInfo: $e")
                responseUserInfo(null)
            }
        }
    }

}