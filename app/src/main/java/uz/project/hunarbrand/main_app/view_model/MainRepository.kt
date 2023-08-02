package uz.project.hunarbrand.main_app.view_model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.project.hunarbrand.db.Database

class MainRepository {
    private val jwtDao = Database.instance.jwtDao()

    suspend fun userIsAuth(): Boolean {
        return withContext(Dispatchers.IO) {
            jwtDao.getJwt()?.access?.isNotEmpty() ?: false
        }
    }
}