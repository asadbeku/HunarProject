package uz.project.hunarbrand.liked.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.project.hunarbrand.databinding.ItemLikedBinding
import java.text.DecimalFormat
import kotlin.math.abs

abstract class LikedBaseHolder(view: View, onClickListener: LikedInterface?) : RecyclerView.ViewHolder(view) {

    private val binding = ItemLikedBinding.bind(view)

    init {
        view.setOnClickListener {
            onClickListener?.onItemClicked(absoluteAdapterPosition )
        }
        binding.likedProductBuy.setOnClickListener {
            onClickListener?.onBuyButtonClick(absoluteAdapterPosition)
        }
        binding.likedProductAddToCart.setOnClickListener {
            onClickListener?.onAddToCartButtonClick(absoluteAdapterPosition)
        }
        binding.productUnlike.setOnClickListener{
            onClickListener?.unlikedButtonClick(absoluteAdapterPosition)
        }
    }

    fun bind(name: String, price: String, image: String) {

        val formatter = DecimalFormat("#,###")
        val formattedPrice = formatter.format(price.toDouble())
        val result = formattedPrice.replace(",", " ")

        binding.likedProductName.text = name
        binding.likedProductPrice.text = "$result so'm"

        Glide.with(itemView.context)
            .load(image)
//            .placeholder(R.drawable.logo_place_holder)
            .into(binding.likedProductImage)
    }
}