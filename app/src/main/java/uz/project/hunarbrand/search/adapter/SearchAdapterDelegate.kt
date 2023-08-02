package uz.project.hunarbrand.search.adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.search.type_view.UserType

class SearchAdapterDelegate(private val onItemClicked: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<UserType, UserType, SearchAdapterDelegate.SearchHolder>() {

    override fun isForViewType(
        item: UserType,
        items: MutableList<UserType>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): SearchHolder {
        return SearchHolder(
            parent.inflate(R.layout.item_search), onItemClicked
        )
    }

    override fun onBindViewHolder(
        item: UserType,
        holder: SearchHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class SearchHolder(view: View, onItemClicked: (position: Int) -> Unit) :
        BaseHolder(view, onItemClicked) {
        fun bind(usersList: UserType) {
            bind(usersList.brand_name.toString(), "${usersList.first_name} ${usersList.last_name}", usersList.logo.toString())
        }
    }
}