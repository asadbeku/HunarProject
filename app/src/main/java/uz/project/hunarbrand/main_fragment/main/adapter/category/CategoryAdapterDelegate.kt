package uz.project.hunarbrand.main_fragment.main.adapter.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.main_fragment.main.types.CategoryType

class CategoryAdapterDelegate(private val onItemClicked: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<CategoryType, CategoryType, CategoryAdapterDelegate.ProductHolder>() {

    override fun isForViewType(
        item: CategoryType,
        items: MutableList<CategoryType>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ProductHolder {
        return ProductHolder(
            parent.inflate(R.layout.item_product), onItemClicked
        )
    }

    override fun onBindViewHolder(
        item: CategoryType,
        holder: ProductHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ProductHolder(view: View, onItemClicked: (position: Int) -> Unit) :
        BaseHolder(view, onItemClicked) {
        fun bind(product: CategoryType) {
            bind(product.image.toString(), product.name_uz.toString())
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}