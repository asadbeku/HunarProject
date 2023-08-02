package uz.project.hunarbrand.splash_screen.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.auth.login.view_types.Jwt
import uz.project.hunarbrand.utils.Bearer

class SplashViewModel : ViewModel() {

    private val repository = SplashRepository()
    private val productDao = Database.instance.productDao()
    private val categoryDao = Database.instance.categoryDao()
    private val userDao = Database.instance.usersDao()
    private val profileDao = Database.instance.profileDao()
    private val jwtDao = Database.instance.jwtDao()

    fun getProducts() {
        repository.getProducts() {
            viewModelScope.launch {
                val list = listOf<ProductType>()

                for (product in it){
                    if (product.soha_uz== null){
                        list.plus(product)
                    }
                }

                productDao.insertProduct(it)
            }
        }
    }

    fun getCategories() {
        repository.getCategories() {
            viewModelScope.launch {
                categoryDao.insertCategory(it)
            }
        }
    }

    fun getProfileInfo() {
        viewModelScope.launch {
            checkJWT()?.let {
                repository.getProfileInfo(it, {
                    viewModelScope.launch {
                        profileDao.insertProfileInfo(it)
                    }
                }, {

                })
            }
        }
    }

    fun getSearchItems() {
        repository.getSearchItems() {
            viewModelScope.launch {
                userDao.insertUsers(it)
            }
        }
    }

    private suspend fun checkJWT(): Int? {
        return withContext(Dispatchers.IO) {
            jwtDao.getJwt()?.let { jwt ->
                val expired = JWT.decode(jwt.access).expiresAt.time
                if (expired < System.currentTimeMillis()) {
                    getRefreshToken(jwt.refresh)
                }
                jwt?.id
            }
        }
    }

    private fun getRefreshToken(refresh: String) {
        repository.refreshToken(refresh) {

        }


    }

}