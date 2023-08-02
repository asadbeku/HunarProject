package uz.project.hunarbrand.profile.profile_info.adapter.my_brand_products

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.ItemMyBrandBinding
import uz.project.hunarbrand.databinding.ItemProductBinding
import uz.project.hunarbrand.databinding.MyBrandProductsBinding

abstract class MyBrandBaseAdapter(
    view: View,
    onItemClicked: (position: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMyBrandBinding.bind(view)

    init {
        view.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(name: String, price: String, image: String) {
        binding.myBrandProductName.text = name
        binding.myBrandProductPrice.text = price

        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.product_500px)
            .into(binding.myBrandProductImage)
    }

}