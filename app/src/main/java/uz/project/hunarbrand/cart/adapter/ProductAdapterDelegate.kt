package uz.project.hunarbrand.cart.adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.cart.view_types.ProductsInCart
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class CartProductAdapterDelegate    (
    private val onClickMinus: (position: Int) -> Unit,
    private val onClickPlus: (position: Int) -> Unit,
    private val onClickDelete: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<ProductsInCart, ProductsInCart, CartProductAdapterDelegate.ProductHolder>() {

    override fun isForViewType(
        item: ProductsInCart,
        items: MutableList<ProductsInCart>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ProductHolder {
        return ProductHolder(
            parent.inflate(R.layout.item_cart), onClickMinus, onClickPlus, onClickDelete
        )
    }

    override fun onBindViewHolder(
        item: ProductsInCart,
        holder: ProductHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ProductHolder(
        view: View,
        onClickMinus: (position: Int) -> Unit,
        onClickPlus: (position: Int) -> Unit,
        onClickDelete: (position: Int) -> Unit
    ) :
        BaseHolder(view, onClickMinus, onClickPlus, onClickDelete) {
        fun bind(product: ProductsInCart) {
            bind(product.name_uz, product.price, product.main_image, product.productCount)
        }
    }
}