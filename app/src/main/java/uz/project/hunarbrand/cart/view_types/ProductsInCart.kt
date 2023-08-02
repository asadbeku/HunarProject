package uz.project.hunarbrand.cart.view_types

data class ProductsInCart(
    val id: Int,
    val name_uz: String,
    val price: String,
    val soha_uz: String,
    val main_image: String,
    var productCount: Int
)