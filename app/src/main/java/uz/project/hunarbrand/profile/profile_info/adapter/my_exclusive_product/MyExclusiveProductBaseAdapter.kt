package uz.project.hunarbrand.profile.profile_info.adapter.my_exclusive_product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.ItemMyExclusiveBinding

abstract class MyExclusiveProductBaseAdapter(view: View, onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(view) {

        private val binding = ItemMyExclusiveBinding.bind(view)

    init {
        view.setOnClickListener{
            onItemClicked(absoluteAdapterPosition)
        }
    }

    fun bind(name: String, price: String, image: String){
        binding.productName.text = name
        binding.productPrice.text = price

        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.product_500px)
            .into(binding.productImage)
    }



}