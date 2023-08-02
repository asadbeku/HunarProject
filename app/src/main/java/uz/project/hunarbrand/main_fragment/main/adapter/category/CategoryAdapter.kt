package uz.project.hunarbrand.main_fragment.main.adapter.category

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import uz.project.hunarbrand.main_fragment.main.types.CategoryType


class CategoryAdapter(private val onItemClicked: (position: Int)-> Unit): AsyncListDifferDelegationAdapter<CategoryType>(CategoryAdapter.ProductDiffUtilCallBack()
) {

    init {
        delegatesManager.addDelegate(
            uz.project.hunarbrand.main_fragment.main.adapter.category.CategoryAdapterDelegate(
                onItemClicked
            )
        )
    }

    class ProductDiffUtilCallBack: DiffUtil.ItemCallback<CategoryType>(){
        override fun areItemsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
            return oldItem==newItem
        }

    }

}