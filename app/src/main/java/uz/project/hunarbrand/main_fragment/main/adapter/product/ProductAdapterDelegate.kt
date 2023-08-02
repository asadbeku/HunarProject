package uz.project.hunarbrand.main_fragment.main.adapter.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.db.Database
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class ProductAdapterDelegate(
    private val onItemClicked: (position: Int) -> Unit,
    private val likedButtonClicked: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<ProductType, ProductType, ProductAdapterDelegate.ProductHolder>() {

    override fun isForViewType(
        item: ProductType,
        items: MutableList<ProductType>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ProductHolder {
        return ProductHolder(
            parent.inflate(R.layout.item_product), onItemClicked, likedButtonClicked
        )
    }

    override fun onBindViewHolder(
        item: ProductType,
        holder: ProductHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ProductHolder(
        view: View,
        onItemClicked: (position: Int) -> Unit,
        likedButtonClicked: (position: Int) -> Unit
    ) : BaseHolder(
        view,
        onItemClicked,
        likedButtonClicked,
        Database.instance.productDao(),
        Database.instance.favouriteProductDao()
    ) {
        fun bind(product: ProductType) {
            bind(product.id, product.name_uz?:"Nomi mavjud emas", product.price, product.main_image?:"null")
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}