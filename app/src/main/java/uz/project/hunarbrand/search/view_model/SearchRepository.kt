package uz.project.hunarbrand.search.view_model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.search.network.GetAllUsersApi
import uz.project.hunarbrand.search.network.GetAllUsersNetwork
import uz.project.hunarbrand.search.type_view.UserType

class SearchRepository {
    //    private var list = listOf<UserType>()
    private val userDao = Database.instance.usersDao()
    suspend fun getCategoryList(response: (products: List<UserType>) -> Unit) {
        response(userDao.getSearchItems())
    }

    suspend fun findCategory(text: String): MutableList<UserType> {
        val list : List<UserType> = userDao.getSearchItems()

        val matchingElements = mutableListOf<UserType>()
        for (element in list) {
            if (text.lowercase() in (element.brand_name.toString()
                    .lowercase()) || text.lowercase() in ("${element.last_name} ${element.first_name}")
            ) {
                matchingElements.add(element)
            }
        }
        return matchingElements
    }
}