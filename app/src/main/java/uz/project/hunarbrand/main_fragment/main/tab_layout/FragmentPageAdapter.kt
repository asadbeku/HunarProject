package uz.project.hunarbrand.main_fragment.main.tab_layout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.project.hunarbrand.main_fragment.main.products.brands.ui.BrandProductsFragment
import uz.project.hunarbrand.main_fragment.main.products.exclusive.ui.ExclusiveProductsFragment

class FragmentPageAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BrandProductsFragment()
            1 -> ExclusiveProductsFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}