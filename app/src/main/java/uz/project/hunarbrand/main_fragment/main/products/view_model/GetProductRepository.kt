package uz.project.hunarbrand.main_fragment.main.products.view_model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.main_fragment.detail.view_types.LikedProduct
import uz.project.hunarbrand.main_fragment.main.types.ProductType


class GetProductRepository {

    private val productDao = Database.instance.productDao()
    private val favouriteProductDao = Database.instance.favouriteProductDao()


    suspend fun getAllProducts(response: (products: List<ProductType>) -> Unit) {
        response(productDao.getAllProducts())
    }

    suspend fun likeProduct(id: Int) {
        val isLiked: Boolean? = favouriteProductDao.getFavouriteProductById(id)

        if (isLiked != null) {
            if (isLiked) {
                favouriteProductDao.removeFavouriteProduct(id)
            } else {

                favouriteProductDao.addFavouriteProduct(LikedProduct(id))
            }
        } else {
            favouriteProductDao.addFavouriteProduct(LikedProduct(id))
        }
    }

}
