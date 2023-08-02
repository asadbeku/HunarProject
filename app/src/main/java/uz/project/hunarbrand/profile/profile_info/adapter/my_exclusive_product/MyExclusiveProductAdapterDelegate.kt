package uz.project.hunarbrand.profile.profile_info.adapter.my_exclusive_product

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.main_fragment.main.adapter.category.inflate
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class MyExclusiveProductAdapterDelegate(private val onItemClicked: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<ProductType, ProductType, MyExclusiveProductAdapterDelegate.MyExclusiveProductHolder>() {

    override fun isForViewType(
        item: ProductType,
        items: MutableList<ProductType>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MyExclusiveProductHolder {
        return MyExclusiveProductHolder(
            parent.inflate(R.layout.item_my_exclusive), onItemClicked
        )
    }

    override fun onBindViewHolder(
        item: ProductType,
        holder: MyExclusiveProductHolder,
        payloads: MutableList<Any>
    ) {
        return holder.bind(item)
    }

    class MyExclusiveProductHolder(view: View, onItemClicked: (position: Int) -> Unit) :
        MyExclusiveProductBaseAdapter(view, onItemClicked) {

        fun bind(myExclusiveProduct: ProductType) {
            bind(
                myExclusiveProduct.name_uz ?: "Nomi mavjud emas",
                myExclusiveProduct.price,
                myExclusiveProduct.main_image ?: "null"
            )
        }

    }
}