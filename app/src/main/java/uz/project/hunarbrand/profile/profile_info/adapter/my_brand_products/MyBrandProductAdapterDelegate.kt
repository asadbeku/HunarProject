package uz.project.hunarbrand.profile.profile_info.adapter.my_brand_products

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.main_fragment.main.adapter.category.inflate
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class MyBrandProductAdapterDelegate (val onItemClicked: (position: Int) -> Unit):
    AbsListItemAdapterDelegate<ProductType, ProductType, MyBrandProductAdapterDelegate.MyBrandProductHolder>() {

    override fun isForViewType(
        item: ProductType,
        items: MutableList<ProductType>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MyBrandProductHolder {
        return MyBrandProductHolder(
            parent.inflate(R.layout.item_my_brand), onItemClicked
        )
    }

    override fun onBindViewHolder(
        item: ProductType,
        holder: MyBrandProductHolder,
        payloads: MutableList<Any>
    ) {
        return holder.bind(item)
    }

    class MyBrandProductHolder(view: View, onItemClicked: (position: Int) -> Unit) :
       MyBrandBaseAdapter(view, onItemClicked) {

        fun bind(myProduct: ProductType) {
            bind(myProduct.name_uz?:"Nomi mavjud emas", myProduct.price, myProduct.main_image?:"null")
        }
    }
}