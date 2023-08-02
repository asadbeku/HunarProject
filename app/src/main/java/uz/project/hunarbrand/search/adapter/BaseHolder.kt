package uz.project.hunarbrand.search.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.project.hunarbrand.databinding.ItemSearchBinding

abstract class BaseHolder(view: View, onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val binding = ItemSearchBinding.bind(view)

    init {
        view.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(categoryName: String, usernameText: String, userImage: String) {
        binding.categoryNameTextView.text = categoryName
        binding.userNameTextView.text = usernameText

        Glide.with(itemView)
            .load(userImage)
            .into(binding.userImageView)
    }
}