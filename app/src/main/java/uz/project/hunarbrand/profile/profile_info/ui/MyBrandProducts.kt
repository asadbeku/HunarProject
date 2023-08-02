package uz.project.hunarbrand.profile.profile_info.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.MyBrandProductsBinding
import uz.project.hunarbrand.profile.profile_info.adapter.my_brand_products.MyBandProductAdapter
import uz.project.hunarbrand.profile.profile_info.view_models.my_products.MyBrandViewModel
import uz.project.hunarbrand.utils.Bearer.getUserId

class MyBrandProducts : Fragment(R.layout.my_brand_products) {

    private val myProductsViewModel: MyBrandViewModel by viewModels()
    private var myBrandProductsAdapter: MyBandProductAdapter? = null

    private var _binding: MyBrandProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyBrandProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observerMyProducts()
    }

    private fun initList() {
        val gridLayout = GridLayoutManager(requireContext(), 2).apply {
            orientation = GridLayoutManager.VERTICAL
        }

        myBrandProductsAdapter =
            MyBandProductAdapter {
                Toast.makeText(requireContext(), "Pressed: $it", Toast.LENGTH_SHORT).show()
            }
        getUserId(requireContext())?.let {
            myProductsViewModel.getBrandProducts(it)
        }

        with(binding.myBrandProducts) {
            adapter = myBrandProductsAdapter
            layoutManager = gridLayout
            setHasFixedSize(true)
        }
    }

    private fun observerMyProducts() {
        myProductsViewModel.myBrandProducts.observe(viewLifecycleOwner) {
            Log.d("checkProduct", "Fragment: Products $it")
            myBrandProductsAdapter?.items = it
        }
    }
}