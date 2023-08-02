package uz.project.hunarbrand.cart.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import uz.project.hunarbrand.R
import uz.project.hunarbrand.cart.adapter.CartProductAdapter
import uz.project.hunarbrand.cart.view_model.CartViewModel
import uz.project.hunarbrand.cart.view_types.ProductsInCart
import uz.project.hunarbrand.databinding.FragmentCartBinding
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import java.text.DecimalFormat

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var cartAdapter: CartProductAdapter? = null
    private val cartViewModel: CartViewModel by viewModels()
    private var cartProducts: List<ProductsInCart>? = null
    private var totalPriceAmount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeCartProducts()
        checkout()
    }

    private fun checkout() {
        binding.checkoutButton.setOnClickListener {

            cartViewModel.errorMessage.observe(viewLifecycleOwner) {
                Snackbar.make(
                    requireView(),
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            cartViewModel.getProfileInfo()

            cartViewModel.profileInfo.observe(viewLifecycleOwner) {

                val numberToList = it.phoneNumber.chunked(1)
                var convertedNumber = ""

                for (i in 3 until numberToList.size) {
                    convertedNumber += numberToList[i]
                }

                var productIds = ""

                cartProducts?.forEach {
                    productIds += it.id.toString() + ","
                }

                showDialog(
                    it.firstName + " " + it.lastName,
                    convertedNumber,
                    it.address,
                    productIds,
                    totalPriceAmount.toString()
                )
            }
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
            if (addressTextView?.text != null && addressTextView.text!!.isNotEmpty()) {

                val data = mapOf(
                    "name" to fullNameTextView?.text.toString(),
                    "phone" to phoneTextView?.text.toString(),
                    "address" to addressTextView.text.toString(),
                    "products" to productsId,
                    "amount" to amount,
                    "status" to "0",
                    "pay_type" to "payme"
                )

                val gson = Gson()
                val json = gson.toJson(data)


                val mediaType = "application/json".toMediaTypeOrNull()
                val requestBody = RequestBody.create(mediaType, json)

                cartViewModel.makeOrder(requestBody, requireContext()) {
                    val uri = Uri.parse(it)
                    val intent = Intent(Intent.ACTION_VIEW, uri)

                    Log.d("checkUrl", "showDialog: $uri")

                    startActivity(intent)
                    dialog.dismiss()
                }

            } else {
                addressTextView?.error = "Maydonni to'ldiring"
            }
        }

        dialog.show()
    }

    private fun initList() {
        cartAdapter = CartProductAdapter(
            {
                cartViewModel.productCountMinus(it)
            }, {
                cartViewModel.productCountPlus(it)
            }, {
                cartViewModel.deleteProductById(it)
            }
        )
        with(binding.cartRecyclerView) {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeCartProducts() {
        cartViewModel.getCartProducts()

        cartViewModel.cartProducts.observe(viewLifecycleOwner) { productsList ->
            Log.d("checkProductInCart", "Fragment: observeCartProducts: $productsList")
            cartAdapter?.items = productsList
            cartProducts = productsList
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            totalPriceAmount = totalPrice * 100
            Log.d("checkProductInCart", "Fragment: observeCartProducts: $totalPrice")
            val formatter = DecimalFormat("#,###")
            val formattedPrice = formatter.format(totalPrice.toDouble())
            val result = formattedPrice.replace(",", " ")
            binding.totalPrice.text = "$result so'm"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}