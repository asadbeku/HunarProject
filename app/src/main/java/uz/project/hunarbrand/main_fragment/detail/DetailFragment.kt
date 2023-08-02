package uz.project.hunarbrand.main_fragment.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smarteist.autoimageslider.SliderView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentDetailProductBinding
import uz.project.hunarbrand.main_fragment.detail.slider.ProductImageSliderAdapter
import uz.project.hunarbrand.main_fragment.detail.view_model.DetailViewModel
import uz.project.hunarbrand.main_fragment.main.types.ProductType
import uz.project.hunarbrand.profile.profile_info.view_types.ProfileInfoType
import uz.project.hunarbrand.utils.Bearer
import java.text.DecimalFormat


class DetailFragment(private var idProduct: Int) : Fragment(R.layout.fragment_detail_product) {
    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!
    private val detailProductViewModel: DetailViewModel by viewModels()
    private lateinit var productSliderAdapter: ProductImageSliderAdapter
    private lateinit var productSliderView: SliderView
    private var productInfo: ProductType? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productObserver()

        buyNow()
        addToCart()

        detailFragmentSettings()
        binding.favouriteButton.setOnClickListener {
            likeProduct()
        }
    }

    private fun likeProduct() {
        detailProductViewModel.likeProduct(idProduct)
    }

    private fun detailFragmentSettings() {
        binding.detailToolBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.favouriteButton.setOnClickListener {

        }

        detailProductViewModel.getProductInfo(id = idProduct)
        detailProductViewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
    }

    private fun buyNow() {

        binding.buyNowButton.setOnClickListener {

            detailProductViewModel.getProfileInfo()

            detailProductViewModel.errorMessage.observe(viewLifecycleOwner) {
                Snackbar.make(
                    requireView(),
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            detailProductViewModel.profileInfo.observe(viewLifecycleOwner) {
                val numberToList = it.phoneNumber.chunked(1)
                var convertedNumber = ""

                for (i in 3 until numberToList.size) {
                    convertedNumber += numberToList[i]
                }

                showDialog(
                    "${it.firstName} ${it.lastName}",
                    convertedNumber,
                    it.address,
                    "$idProduct",
                    "${productInfo?.price?.toDouble()!! * 100}"
                )


            }


        }
    }


    private fun addToCart() {
        binding.addToCartButton.setOnClickListener {
            if (productInfo != null) {
                productInfo?.let {
                    detailProductViewModel.addProductToCart(it.id)
                }
                Snackbar.make(requireView(), "Savatga qo'shildi", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(requireView(), "Product not founded", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun productObserver() {
        detailProductViewModel.detailProduct.observe(viewLifecycleOwner) { product ->

            productInfo = product

            val productImages: List<String> =
                listOf(
                    product.main_image ?: "null",
                    product.image_2 ?: "null",
                    product.image_3 ?: "null",
                    product.image_4 ?: "null"
                )

            productSliderView = binding.detailProductImageSlider
            productSliderAdapter = ProductImageSliderAdapter(productImages)
            productSliderView.setSliderAdapter(productSliderAdapter)
            productSliderView.scrollTimeInSec = productImages.size

            binding.detailProductImageSlider
            binding.detailProductNameTextView.text = product.name_uz

            val formatter = DecimalFormat("#,###")
            val formattedPrice = formatter.format(product.price.toDouble())
            val result = formattedPrice.replace(",", " ")

            binding.detailProductPrice.text = result + " so'm"
            binding.detailInfoCompound.text = "Maxsulot tarkibi :\t${product.compound_uz}"
            binding.detailInfoWeight.text = "Og'irligi :\t\t${product.weight} ${product.massa}"
            binding.detailInfoDirection.text = "Yo'nalish :\t\t\t\t${product.soha_uz}"

            detailProductViewModel.isLiked(product.id)
            detailProductViewModel.isLiked.observe(viewLifecycleOwner) {
                if (it) {
                    binding.favouriteIcon.setImageResource(R.drawable.favourite_like)
                } else {
                    binding.favouriteIcon.setImageResource(R.drawable.icon_favourite)
                }
            }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.updateContainer.visibility = View.VISIBLE
            binding.detailContainer.visibility = View.GONE
        } else {
            binding.updateContainer.visibility = View.GONE
            binding.detailContainer.visibility = View.VISIBLE
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
            if (addressTextView?.text?.toString()!!.isNotEmpty()) {

                val data = mapOf(
                    "name" to fullNameTextView?.text.toString(),
                    "phone" to phoneTextView?.text.toString(),
                    "address" to addressTextView?.text.toString(),
                    "products" to productsId,
                    "amount" to amount,
                    "status" to "0",
                    "pay_type" to "payme"
                )

                val gson = Gson()
                val json = gson.toJson(data)


                val mediaType = "application/json".toMediaTypeOrNull()
                val requestBody = RequestBody.create(mediaType, json)

                detailProductViewModel.buyProduct(requestBody) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}