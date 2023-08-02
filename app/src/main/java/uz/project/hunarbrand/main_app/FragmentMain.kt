package uz.project.hunarbrand.main_app

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.project.hunarbrand.R
import uz.project.hunarbrand.cart.ui.CartFragment
import uz.project.hunarbrand.liked.ui.LikeFragment
import uz.project.hunarbrand.main_app.view_model.MainViewModel
import uz.project.hunarbrand.main_fragment.main.ui.ui.TabsFragment
import uz.project.hunarbrand.profile.profile_info.ui.ProfileFragment
import uz.project.hunarbrand.profile.profile_info.ui.ProfileUnAuthFragment
import uz.project.hunarbrand.search.ui.SearchFragment

class FragmentMain : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottom = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        setCurrentFragment(TabsFragment())

        Log.d("TTT", "onViewCreated: ${checkAuth()}")
        bottom.setOnItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.page_1 -> TabsFragment()
                R.id.page_2 -> SearchFragment()
                R.id.page_3 -> LikeFragment()
                R.id.page_4 -> CartFragment()
                R.id.page_5 -> {
                    if (checkAuth()) {
                        Log.d("checkAuth", "onViewCreated: ProfileFragment")
                        ProfileFragment()
                    } else {
                        Log.d("checkAuth", "onViewCreated: UnAuthFragment")
                        ProfileUnAuthFragment()
                    }
                }
                else -> throw IllegalArgumentException("Invalid menu item")
            }

            setCurrentFragmentWithAnimation(fragment)
            true
        }
    }

    private fun checkAuth(): Boolean {
        return mainViewModel.userIsAuth()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

    private fun setCurrentFragmentWithAnimation(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            .replace(R.id.flFragment, fragment)
            .commit()
    }
}