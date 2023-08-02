package uz.project.hunarbrand.main_fragment.main.products.exclusive.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentExclusiveProductsBinding
import uz.project.hunarbrand.main_fragment.detail.DetailFragment
import uz.project.hunarbrand.main_fragment.main.adapter.product.ProductAdapter
import uz.project.hunarbrand.main_fragment.main.products.exclusive.view_model.ExclusiveViewModel

class ExclusiveProductsFragment : Fragment(R.layout.fragment_exclusive_products) {

    private val mainAppLivedata: ExclusiveViewModel by viewModels()
    private var productAdapter: ProductAdapter? = null

    private var _binding: FragmentExclusiveProductsBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExclusiveProductsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observeProducts()
    }

    private fun initList() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2).apply {
            orientation = GridLayoutManager.VERTICAL
        }

        lifecycleScope.launch {
            mainAppLivedata.getProducts()
        }
//        mainAppLivedata.getProducts()

        productAdapter = ProductAdapter({ position ->
            val list = mainAppLivedata.products.value

            if (list != null) {
                val productId: Int = list[position].id

                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fullContainer, DetailFragment(productId))
                    addToBackStack("")
                    commit()
                }
            }
        }, {
            val list = mainAppLivedata.products.value

            if(list !=null){
                val productId: Int = list[it].id
                mainAppLivedata.likeProduct(productId)
            }
        })

        with(binding.recycler) {
            adapter = productAdapter
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun observeProducts() {
        mainAppLivedata.products.observe(viewLifecycleOwner) { list ->
            productAdapter?.items = list
        }
    }

}