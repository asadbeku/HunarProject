package uz.project.hunarbrand.main_fragment.main.products.brands.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentBrandProductsBinding
import uz.project.hunarbrand.main_fragment.detail.DetailFragment
import uz.project.hunarbrand.main_fragment.main.adapter.product.ProductAdapter
import uz.project.hunarbrand.main_fragment.main.products.brands.view_model.BrandViewModel

class BrandProductsFragment : Fragment(R.layout.fragment_brand_products) {
    private val brandViewModel: BrandViewModel by viewModels()
    private var productAdapter: ProductAdapter? = null

    private var _binding: FragmentBrandProductsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrandProductsBinding.inflate(inflater, container, false)
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

        brandViewModel.getProducts()

        productAdapter = ProductAdapter({ position ->

            val list = brandViewModel.products.value

            if (list != null) {
                val productId: Int = list[position].id

                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fullContainer, DetailFragment(productId))
                    addToBackStack("")
                    commit()
                }
            }
        }, {
            val list = brandViewModel.products.value

            Log.d("checkDBProductRepo","likeProduct: $it")

//            if(list !=null){
//                val productId: Int = list[it].id
//                brandViewModel.likeProduct(productId)
//                Log.d("checkDBProductRepo","likeProduct: $productId")
//            }
        })

        with(binding.recycler) {
            adapter = productAdapter
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun observeProducts() {
        brandViewModel.products.observe(viewLifecycleOwner) { list ->
            productAdapter?.items = list
        }
    }
}