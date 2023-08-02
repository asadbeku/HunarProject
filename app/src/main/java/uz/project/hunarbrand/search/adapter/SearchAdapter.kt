package uz.project.hunarbrand.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import uz.project.hunarbrand.search.type_view.UserType

class SearchAdapter(private val onItemClicked: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<UserType>(
        DifUtilCallBack()
    ) {

    init {
        delegatesManager.addDelegate(SearchAdapterDelegate(onItemClicked))
    }

    class DifUtilCallBack : DiffUtil.ItemCallback<UserType>() {
        override fun areItemsTheSame(oldItem: UserType, newItem: UserType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserType, newItem: UserType): Boolean {
            return oldItem == newItem
        }
    }

}