package uz.project.hunarbrand.main_fragment.main.adapter.slider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import uz.project.hunarbrand.R
import uz.project.hunarbrand.main_fragment.main.types.CategoryResList


class ImageSliderAdapter(categoryItems: List<CategoryResList>): SliderViewAdapter<ImageSliderAdapter.SliderViewHolder>() {

    private var sliderList: List<CategoryResList> = categoryItems

    override fun getCount(): Int {
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val inflate: View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.item_category, null)


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
                .load(sliderList[position].categoryImage)
                .centerCrop()
                .into(viewHolder.imageView)

            viewHolder.textView.text = sliderList[position].categoryText
        }
    }

    class SliderViewHolder(itemView: View?) : ViewHolder(itemView) {
        var imageView: ImageView = itemView!!.findViewById(R.id.categoryImage)
        var textView : TextView = itemView!!.findViewById(R.id.categoryNameTextView)
    }

}