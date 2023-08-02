package uz.project.hunarbrand.profile.profile_info.adapter.my_exclusive_product

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class MyExclusiveProductAdapter(private val onItemClicked: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<ProductType>(MyExclusiveProductDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MyExclusiveProductAdapterDelegate(onItemClicked))
    }

    class MyExclusiveProductDiffUtilCallback() : DiffUtil.ItemCallback<ProductType>() {
        override fun areItemsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem == newItem
        }
    }
}