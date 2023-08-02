package uz.project.hunarbrand.db.models

object ProductsInCartContract {

    const val TABLE_NAME = "products_in_cart"

    object Columns {
        const val ID = "id"
        const val PRODUCT_ID = "product_id"
        const val USER_ID = "user_id"
        const val QUANTITY = "quantity"
    }

}