package uz.project.hunarbrand.cart.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import uz.project.hunarbrand.cart.view_types.ProductsInCart
import uz.project.hunarbrand.main_fragment.main.types.ProductType

class CartProductAdapter(
    onClickMinus: (position: Int) -> Unit,
    onClickPlus: (position: Int) -> Unit,
    onClickDelete: (position: Int) -> Unit
) :
    AsyncListDifferDelegationAdapter<ProductsInCart>(
        ProductDiffUtilCallBack()
    ) {

    fun submitList(list: List<ProductsInCart>) {
        items = list
    }

    init {
        delegatesManager.addDelegate(CartProductAdapterDelegate(onClickMinus, onClickPlus, onClickDelete))
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductsInCart>() {
        override fun areItemsTheSame(oldItem: ProductsInCart, newItem: ProductsInCart): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductsInCart, newItem: ProductsInCart): Boolean {
            return oldItem.productCount == newItem.productCount
        }
    }

}