package uz.project.hunarbrand.main_fragment.main.ui.view_model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.main_fragment.main.types.CategoryType
import uz.project.hunarbrand.main_fragment.main.ui.network.GetAllCategoryApi
import uz.project.hunarbrand.main_fragment.main.ui.network.GetAllCategoryNetwork
import uz.project.hunarbrand.search.network.GetAllUsersApi
import uz.project.hunarbrand.search.network.GetAllUsersNetwork

class CategoryRepository {

    private val categoryDao = Database.instance.categoryDao()

    suspend fun getAllCategory(response: (products: List<CategoryType>) -> Unit) {
        response(categoryDao.getAllCategories())
        Log.d("checkCategory", "Repository: ${categoryDao.getAllCategories()}")
    }

}