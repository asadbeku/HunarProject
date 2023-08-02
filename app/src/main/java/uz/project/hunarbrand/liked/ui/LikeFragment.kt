package uz.project.hunarbrand.liked.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentLikeBinding
import uz.project.hunarbrand.liked.adapter.LikedAdapter
import uz.project.hunarbrand.liked.adapter.LikedAdapterDelegate
import uz.project.hunarbrand.liked.adapter.LikedInterface
import uz.project.hunarbrand.liked.view_model.LikedViewModel
import uz.project.hunarbrand.liked.view_types.BuyProductType
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType

class LikeFragment : Fragment(R.layout.fragment_like), LikedInterface {

    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!
    private var likedAdapter: LikedAdapter? = null
    private val viewModel: LikedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observer()

    }

    private fun initList() {
        likedAdapter = LikedAdapter(this)
        val linear = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        with(binding.recyclerView) {
            adapter = likedAdapter
            layoutManager = linear
            setHasFixedSize(true)
        }


    }

    private fun observer() {
        viewModel.getLikedProducts()
        viewModel.likedProducts.observe(viewLifecycleOwner) {
            likedAdapter?.items = it
        }

    }

    override fun onBuyButtonClick(position: Int) {
        generateProductToBy(position) {
            showDialog(it.name, it.phone, it.address, it.productsId[0], it.amount)
        }
    }

    override fun onAddToCartButtonClick(position: Int) {
        viewModel.addProductToCart(position) {
            Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun unlikedButtonClick(position: Int) {

        viewModel.removeFromFavourite(position) {
            Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        Snackbar.make(
            requireView(),
            "Pressed unlike button position: $position",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onItemClicked(position: Int) {
        Snackbar.make(requireView(), "Pressed item position: $position", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun generateProductToBy(position: Int, response: (product: BuyProductType) -> Unit) {
        viewModel.generateProductToBuy(position) {
            response(it)
        }
    }

    private fun showDialog(
        name: String,
        phone: String,
        address: String?,
        productsId: String,
        amount: String
    ) {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.fragment_buy_now)
        val fullNameTextView = dialog.findViewById<TextInputLayout>(R.id.fullnameTextView)?.editText
        val addressTextView = dialog.findViewById<TextInputLayout>(R.id.addressTextView)?.editText
        val phoneTextView = dialog.findViewById<TextInputLayout>(R.id.phoneTextView)?.editText

        fullNameTextView?.setText(name)
        addressTextView?.setText(address)
        phoneTextView?.setText(phone)

        dialog.findViewById<View>(R.id.buyNowButton)?.setOnClickListener {
            if (checkFields(fullNameTextView, phoneTextView, addressTextView)) {
                val convertedAmount = amount.toDouble() * 100
                val data = mapOf(
                    "name" to fullNameTextView?.text.toString(),
                    "phone" to phoneTextView?.text.toString(),
                    "address" to addressTextView?.text.toString(),
                    "products" to productsId,
                    "amount" to convertedAmount,
                    "status" to "0",
                    "pay_type" to "payme"
                )

                val gson = Gson()
                val json = gson.toJson(data)

                val mediaType = "application/json".toMediaTypeOrNull()
                val requestBody = RequestBody.create(mediaType, json)

                viewModel.buyProduct(requestBody) {
                    val uri = Uri.parse(it)
                    val intent = Intent(Intent.ACTION_VIEW, uri)

                    Log.d("checkUrl", "showDialog: $uri")

                    startActivity(intent)
                    dialog.dismiss()
                }

            }


        }

        dialog.show()


    }

    private fun checkFields(
        fullNameTextView: EditText?,
        phoneTextView: EditText?,
        addressTextView: EditText?
    ): Boolean {
        var result = true

        val fullName = fullNameTextView?.text?.toString()
        val phone = phoneTextView?.text?.toString()
        val address = addressTextView?.text?.toString()

        if (fullName.isNullOrEmpty()) {
            fullNameTextView?.error = "Ismingizni kiriting"
            result = false
        } else if (fullName.length < 3) {
            fullNameTextView?.error = "Ismingiz kamida 3 ta belgidan iborat bo'lishi kerak"
            result = false
        }

        if (phone.isNullOrEmpty()) {
            phoneTextView?.error = "Telefon raqamingizni kiriting"
            result = false
        } else if (phone.length < 9) {
            phoneTextView?.error = "Telefon raqamingizni noto'g'ri formatda kiritdingiz"
            result = false
        }

        if (address.isNullOrEmpty()) {
            addressTextView?.error = "Manzilingizni kiriting"
            result = false
        }

        return result

    }

}