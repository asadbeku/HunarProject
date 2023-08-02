package uz.project.hunarbrand.profile.profile_info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.MyExclusiveProductsBinding
import uz.project.hunarbrand.profile.profile_info.adapter.my_exclusive_product.MyExclusiveProductAdapter
import uz.project.hunarbrand.profile.profile_info.view_models.my_products.MyExclusiveViewModel
import uz.project.hunarbrand.utils.Bearer

class MyExclusiveProducts : Fragment(R.layout.my_exclusive_products) {

    private val myExclusiveProductsViewModel: MyExclusiveViewModel by viewModels()
    private var myExclusiveAdapter: MyExclusiveProductAdapter? = null

    private var _binding: MyExclusiveProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyExclusiveProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observerMyExclusiveProduct()
    }

    private fun initList() {
            val gridLayout = GridLayoutManager(requireContext(), 2).apply {
            orientation = GridLayoutManager.VERTICAL
        }
        Bearer.getUserId(requireContext())?.let {
            myExclusiveProductsViewModel.getExclusiveProducts(it)
        }

        myExclusiveAdapter = MyExclusiveProductAdapter {
            Toast.makeText(requireContext(), "Position: $it", Toast.LENGTH_SHORT).show()
        }

        with(binding.myExclusiveProducts){
            adapter = myExclusiveAdapter
            layoutManager = gridLayout
            setHasFixedSize(true)
        }
    }

    private fun observerMyExclusiveProduct() {
        myExclusiveProductsViewModel.myExclusiveProducts.observe(viewLifecycleOwner){
            myExclusiveAdapter?.items = it
        }
    }
}