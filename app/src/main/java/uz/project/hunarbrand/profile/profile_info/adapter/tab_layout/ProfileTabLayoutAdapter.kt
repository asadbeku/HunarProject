package uz.project.hunarbrand.profile.profile_info.adapter.tab_layout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.project.hunarbrand.profile.profile_info.ui.MyBrandProducts
import uz.project.hunarbrand.profile.profile_info.ui.MyExclusiveProducts

class ProfileTabLayoutAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyBrandProducts()
            1 -> MyExclusiveProducts()
            else -> throw IllegalArgumentException("Invalid tab position")

        }
    }
}