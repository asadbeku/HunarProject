package uz.project.hunarbrand.liked.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class LikedAdapter(onClickListener: LikedInterface) : AsyncListDifferDelegationAdapter<ProductType>(LikedAdapterDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(LikedAdapterDelegate(onClickListener))
    }

    class LikedAdapterDiffUtilCallback() : DiffUtil.ItemCallback<ProductType>() {
        override fun areItemsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem == newItem
        }

    }

}