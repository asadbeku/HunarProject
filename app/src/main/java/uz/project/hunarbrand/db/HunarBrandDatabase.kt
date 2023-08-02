package uz.project.hunarbrand.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.project.hunarbrand.cart.view_types.CartType
import uz.project.hunarbrand.main_fragment.detail.view_types.LikedProduct
import uz.project.hunarbrand.main_fragment.main.types.CategoryType
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.auth.login.view_types.Jwt
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import uz.project.hunarbrand.search.type_view.UserType

@Database(
    entities = [
        ProductType::class,
        CategoryType::class,
        UserType::class,
        Jwt::class,
        ProfileInfoType::class,
        LikedProduct::class,
        CartType::class],
    version = HunarBrandDatabase.DB_VERSION,
    exportSchema = false
)
abstract class HunarBrandDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun usersDao(): UsersDao
    abstract fun jwtDao(): JwtDao
    abstract fun profileDao(): ProfileDao
    abstract fun favouriteProductDao(): FavouriteProductDao
    abstract fun cartDao(): CartDao

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "hunar_brand_db"
    }
}