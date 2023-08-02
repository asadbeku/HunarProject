package uz.project.hunarbrand.profile.profile_info.adapter.my_brand_products

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class MyBandProductAdapter(onItemClicked: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<ProductType>(MyBrandProductDiffUtilCallBack()) {

    init {
        delegatesManager.addDelegate(
            MyBrandProductAdapterDelegate(
                onItemClicked
            )
        )
    }

    class MyBrandProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductType>() {
        override fun areItemsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductType, newItem: ProductType): Boolean {
            return oldItem == newItem
        }

    }
}