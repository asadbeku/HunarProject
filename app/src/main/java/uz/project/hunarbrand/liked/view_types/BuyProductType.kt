package uz.project.hunarbrand.liked.view_types

data class BuyProductType(
    val name: String,
    val phone: String,
    val address: String?,
    val productsId: List<String>,
    val amount: String
)