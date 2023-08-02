package uz.project.hunarbrand.main_fragment.main.ui.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.SliderView
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.ActivityTabsBinding
import uz.project.hunarbrand.main_fragment.main.adapter.slider.ImageSliderAdapter
import uz.project.hunarbrand.main_fragment.detail.slider.ProductImageSliderAdapter
import uz.project.hunarbrand.main_fragment.main.tab_layout.FragmentPageAdapter
import uz.project.hunarbrand.main_fragment.main.types.CategoryResList
import uz.project.hunarbrand.main_fragment.main.ui.view_model.CategoryViewModel


class TabsFragment : Fragment(R.layout.activity_tabs) {

    private var _binding: ActivityTabsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sliderView: SliderView

    private val categoryViewModel: CategoryViewModel by viewModels()

    private lateinit var sliderAdapter: ImageSliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = ActivityTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindTabLayout()
        categoryViewModel.getCategory()
        categoryObserver()
    }

    private fun bindTabLayout() {
        val adapter = FragmentPageAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Brands"
                1-> tab.text = "Exclusive"
            }
        }.attach()
    }

    private fun bindSliderViewPager(categoryList: List<CategoryResList>) {
        sliderView = binding.categorySlider

        sliderAdapter = ImageSliderAdapter(categoryList)

        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        sliderView.setSliderAdapter(sliderAdapter)

        sliderView.scrollTimeInSec = 3

        sliderView.isAutoCycle = true

        sliderView.startAutoCycle()
    }

    private fun categoryObserver(){
        categoryViewModel.categoryList.observe(viewLifecycleOwner){
            Log.d("checkCategory","$it")
            bindSliderViewPager(it)
        }
    }
}