package uz.project.hunarbrand.cart.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.ItemCartBinding
import uz.project.hunarbrand.databinding.ItemProductBinding
import java.text.DecimalFormat

abstract class BaseHolder(
    view: View,
    private val onClickMinus: (position: Int) -> Unit,
    private val onClickPlus: (position: Int) -> Unit,
    private val onClickDelete: (position: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemCartBinding.bind(view)

    init {
        binding.minusCountButton.setOnClickListener {
            onClickMinus(absoluteAdapterPosition)
        }
        binding.addCountButton.setOnClickListener {
            onClickPlus(absoluteAdapterPosition)
        }
        binding.deleteButton.setOnClickListener {
            onClickDelete(absoluteAdapterPosition)
        }
    }

    fun bind(name: String, price: String, image: String, productCount: Int) {
        val formatter = DecimalFormat("#,###")
        val formattedPrice = formatter.format(price.toDouble())
        val result = formattedPrice.replace(",", " ")

        binding.cartProductName.text = name
        binding.cartProductPrice.text = "$result so'm"
        binding.productCountButton.text = productCount.toString()

        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.product_500px)
            .into(binding.cartProductImage)
    }
}