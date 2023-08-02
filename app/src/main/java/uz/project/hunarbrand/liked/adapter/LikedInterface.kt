package uz.project.hunarbrand.liked.adapter

interface LikedInterface {

    fun onBuyButtonClick(position: Int)

    fun onAddToCartButtonClick(position: Int)

    fun unlikedButtonClick(position: Int)

    fun onItemClicked(position: Int)

}