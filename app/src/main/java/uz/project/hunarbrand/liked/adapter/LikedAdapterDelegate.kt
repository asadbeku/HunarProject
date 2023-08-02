package uz.project.hunarbrand.liked.adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.search.adapter.inflate

class LikedAdapterDelegate(private var onClickListener: LikedInterface) :
    AbsListItemAdapterDelegate<ProductType, ProductType, LikedAdapterDelegate.LikedHolder>() {

    override fun isForViewType(
        item: ProductType,
        items: MutableList<ProductType>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): LikedHolder {
        return LikedHolder(
            parent.inflate(R.layout.item_liked), onClickListener
        )
    }

    override fun onBindViewHolder(
        item: ProductType,
        holder: LikedHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: LikedInterface) {
        this.onClickListener = onClickListener
    }

    class LikedHolder(
        view: View,
        onClickListener: LikedInterface?
    ) : LikedBaseHolder(view, onClickListener) {
        fun bind(product: ProductType) {
            bind(product.name_uz ?: "Nomi mavjud emas", product.price, product.main_image ?: "null")
        }
    }
}