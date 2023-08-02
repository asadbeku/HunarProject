package uz.project.hunarbrand.main_fragment.detail.slider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import uz.project.hunarbrand.R


class ProductImageSliderAdapter(categoryItems: List<String>): SliderViewAdapter<ProductImageSliderAdapter.SliderViewHolder>() {

    private var sliderList: List<String> = categoryItems

    override fun getCount(): Int {
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val inflate: View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.product_image_slider, null)


        // on below line we are simply passing
        // the view to our slider view holder.
        return SliderViewHolder(inflate)
    }

    // on below line we are calling on bind view holder method to set the data to our image view.
    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {

        // on below line we are checking if the view holder is null or not.
        if (viewHolder != null) {
            // if view holder is not null we are simply
            // loading the image inside our image view using glide library
            Glide.with(viewHolder.itemView)
                .load(sliderList[position])
                .centerCrop()
                .into(viewHolder.imageView)
        }
    }

    class SliderViewHolder(itemView: View?) : ViewHolder(itemView) {
        var imageView: ImageView = itemView!!.findViewById(R.id.productImageSlider)
    }

}