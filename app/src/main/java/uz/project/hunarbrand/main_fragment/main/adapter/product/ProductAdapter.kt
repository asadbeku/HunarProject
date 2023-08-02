package uz.project.hunarbrand.main_fragment.main.adapter.product

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class ProductAdapter(
    onItemClicked: (position: Int) -> Unit,
    likedButtonClicked: (position: Int) -> Unit
) :
    AsyncListDifferDelegationAdapter<ProductType>(
        ProductDiffUtilCallBack()
    ) {

    init {
        delegatesManager.addDelegate(ProductAdapterDelegate(onItemClicked, likedButtonClicked))
    }


    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductType>() {
        override fun areItemsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem == newItem
        }

    }

}