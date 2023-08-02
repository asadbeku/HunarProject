package uz.project.hunarbrand.main_fragment.main.adapter.product

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.ItemProductBinding
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.db.FavouriteProductDao
import uz.project.hunarbrand.db.ProductDao
import uz.project.hunarbrand.main_fragment.detail.view_types.LikedProduct
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import java.text.DecimalFormat

abstract class BaseHolder(
    view: View,
    onItemClicked: (position: Int) -> Unit,
    likedButtonClicked: (position: Int) -> Unit,
    private val productDao: ProductDao,
    private val favouriteDao: FavouriteProductDao,
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemProductBinding.bind(view)

    init {
        view.setOnClickListener {
            onItemClicked(absoluteAdapterPosition)
        }

        binding.liked.setOnClickListener {
//            likedButtonClicked(absoluteAdapterPosition)
            Log.d("iconChange", "clicked")
            toggleLikeStatus()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun toggleLikeStatus() {
        GlobalScope.launch(Dispatchers.IO) {
            val productList: List<ProductType> = productDao.getAllProducts()
            val productIsLiked: Boolean =
                favouriteDao.getFavouriteProductById(productList[absoluteAdapterPosition].id)
                    ?: false

            Log.d("iconChange", "product: $productIsLiked")
            productIsLiked.let { isLiked ->
                if (isLiked) {
                    withContext(Dispatchers.Main) {
                        favouriteDao.removeFavouriteProduct(productList[absoluteAdapterPosition].id)
                    }
                    updateLikeIcon(false)
                } else {
                    withContext(Dispatchers.Main) {
                        favouriteDao.addFavouriteProduct(LikedProduct(productList[absoluteAdapterPosition].id))
//                        productDao.likeProduct(productList[absoluteAdapterPosition].id, true)
                    }
                    updateLikeIcon(true)
                }
            }
        }
    }

    private fun updateLikeIcon(isLiked: Boolean) {
        if (isLiked) {
            binding.liked.setImageResource(R.drawable.liked_icon)
            Log.d("iconChange", "liked, isLiked: $isLiked")
        } else {
            Log.d("iconChange", "unliked, isLiked: $isLiked")
            binding.liked.setImageResource(R.drawable.unliked_icon)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun isLiked(id: Int) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val isFavourite = favouriteDao.getFavouriteProductById(id) ?: false

                if (isFavourite) {
                    updateLikeIcon(true)
                } else {
                    updateLikeIcon(false)
                }
            }
        }
    }

    fun bind(id: Int, name: String, price: String, image: String) {

        isLiked(id)

        val formatter = DecimalFormat("#,###")
        val formattedPrice = formatter.format(price.toDouble())
        val result = formattedPrice.replace(",", " ")

        binding.productName.text = name
        binding.productPrice.text = "$result so'm"

        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.logo_place_holder)
            .into(binding.productImage)
    }
}
