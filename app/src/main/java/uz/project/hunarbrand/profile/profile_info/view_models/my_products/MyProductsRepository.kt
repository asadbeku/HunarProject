package uz.project.hunarbrand.profile.profile_info.view_models.my_products

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.network.my_brand_products.MyBrandProductsApi
import uz.project.hunarbrand.profile.profile_info.network.my_brand_products.MyBrandProductsNetwork
import uz.project.hunarbrand.profile.profile_info.view_types.MyProducts

class MyProductsRepository {

    fun getProducts(id: Int, callback: (List<ProductType>) -> Unit) {
        MyBrandProductsNetwork.buildService(MyBrandProductsApi::class.java)
            .getProductsById(id)
            .enqueue(object : Callback<MyProducts> {
                override fun onResponse(
                    call: Call<MyProducts>,
                    response: Response<MyProducts>
                ) {
                    if (response.isSuccessful) {
                        val myProducts = response.body()
                        val results = myProducts?.results ?: emptyList()
                        callback(results)
                    } else {
                        // Handle error response
                    }
                }

                override fun onFailure(call: Call<MyProducts>, t: Throwable) {
                    // Handle network failure
                }
            })
    }


//    fun myExclusive(): List<ProductType> {
//        for (item in productList) {
//            if (!item.e_status) {
//                myExclusiveProduct = myExclusiveProduct + item
//            }
//        }
//        return myExclusiveProduct
//    }
//        fun myBrand(): List<ProductType>{
//        for (item in productList){
//            if (item.e_status){
//                myBrandProduct = myBrandProduct + item
//            }
//        }
//        Log.d("checkProduct","Products: $myBrandProduct")
//        return myBrandProduct
//    }
//
//    fun myExclusive(): List<ProductType>{
//        for (item in productList){
//            if (!item.e_status){
//                myExclusiveProduct = myExclusiveProduct + item
//            }
//        }
//        return myExclusiveProduct
//    }

//    private var productList = listOf(
//        ProductType(
//            2,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Suvenier",
//            null,
//            null,
//            353,
//            "g",
//            "tut daraxti",
//            null,
//            null,
//            "1000000.00",
//            true,
//            false,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA12.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA11.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA10.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA9.jpg"
//        ),
//        ProductType(
//            4,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Soat",
//            null,
//            null,
//            1803,
//            "g",
//            "Yasin daraxti",
//            null,
//            null,
//            "100000.00",
//            true,
//            true,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA17.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA18.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA19.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA20.jpg"
//        ),
//        ProductType(
//            6,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Mevalar uchun idish",
//            null,
//            null,
//            354,
//            "g",
//            "Chinor daraxti",
//            null,
//            null,
//            "100000.00",
//            true,
//            true,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA25.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA26.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA27.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA28.jpg"
//        ),
//        ProductType(
//            2,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Suvenier",
//            null,
//            null,
//            353,
//            "g",
//            "tut daraxti",
//            null,
//            null,
//            "1000000.00",
//            true,
//            true,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA12.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA11.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA10.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA9.jpg"
//        ),
//        ProductType(
//            4,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Soat",
//            null,
//            null,
//            1803,
//            "g",
//            "Yasin daraxti",
//            null,
//            null,
//            "100000.00",
//            true,
//            false,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA17.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA18.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA19.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA20.jpg"
//        ),
//        ProductType(
//            6,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Mevalar uchun idish",
//            null,
//            null,
//            354,
//            "g",
//            "Chinor daraxti",
//            null,
//            null,
//            "100000.00",
//            true,
//            true,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA25.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA26.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA27.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA28.jpg"
//        ),
//        ProductType(
//            2,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Suvenier",
//            null,
//            null,
//            353,
//            "g",
//            "tut daraxti",
//            null,
//            null,
//            "1000000.00",
//            true,
//            true,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA12.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA11.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA10.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA9.jpg"
//        ),
//        ProductType(
//            4,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Soat",
//            null,
//            null,
//            1803,
//            "g",
//            "Yasin daraxti",
//            null,
//            null,
//            "100000.00",
//            true,
//            false,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA17.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA18.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA19.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA20.jpg"
//        ),
//        ProductType(
//            6,
//            "Yog'och o'ymakorligi",
//            null,
//            null,
//            13,
//            "Mevalar uchun idish",
//            null,
//            null,
//            354,
//            "g",
//            "Chinor daraxti",
//            null,
//            null,
//            "100000.00",
//            true,
//            false,
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA25.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA26.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA27.jpg",
//            "https://edumonitoring.uz/media/product/image/%D0%A0%D0%B8%D1%81%D1%83%D0%BD%D0%BE%D0%BA28.jpg"
//        )
//    )

    private var myBrandProduct = listOf<ProductType>()
    private var myExclusiveProduct = listOf<ProductType>()

}