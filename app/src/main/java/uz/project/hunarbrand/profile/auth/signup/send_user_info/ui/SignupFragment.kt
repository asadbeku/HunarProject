package uz.project.hunarbrand.profile.auth.signup.send_user_info.ui

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hunarbrend.signup.otpauth.viewModel.Category.CategoryViewModel
import com.example.hunarbrend.signup.SendUserInfo.Utills.UploadImageRequestBody
import uz.project.hunarbrand.profile.auth.signup.send_user_info.ViewModel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import okhttp3.MultipartBody
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentChangeUserInfoBinding
import uz.project.hunarbrand.profile.auth.login.ui.LoginFragment
import uz.project.hunarbrand.profile.auth.signup.send_user_info.Utills.DropdownAdapter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class SignupFragment : Fragment(R.layout.fragment_change_user_info),
    UploadImageRequestBody.UploadCallback {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private var selectedAvatarImageUri: Uri? = null
    private var selectedLogoImageUri: Uri? = null
    private lateinit var categoryAdapter: List<String>
    private var _binding: FragmentChangeUserInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryDropDown()

        binding.uploadAvatarImageButton.setOnClickListener {
            getPhotoFromPhone()
        }

        binding.uploadLogoButton.setOnClickListener {
            getPhotoUri()
        }

        binding.registrationButton.setOnClickListener {
            checkFielding()
            sendUserData()
            checkPassword()
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }

    private fun checkPassword(): Boolean {
        var status = false
        val password = binding.passwordEditText.editText?.text.toString().trim()
        val confirmPassword = binding.conformPasswordEditText.editText?.text.toString().trim()
        if (password != confirmPassword) {
            binding.passwordEditText.error = "Parol mos kelmadi"
            binding.conformPasswordEditText.error = "Parol mos kelmadi"
        } else {
            if (password.length < 8 && !password.matches(Regex("[a-zA-Z]+"))) {
                binding.passwordEditText.error =
                    "Parol 8 ta belgidan va belgilar katta yoki kichik harflardan iborat bo'lishi kerak"
                binding.conformPasswordEditText.error =
                    "Parol 8 ta belgidan va belgilar katta yoki kichik harflardan iborat bo'lishi kerak"
            } else {
                binding.passwordEditText.error = null
                binding.conformPasswordEditText.error = null
                status = true
            }
        }
        return status
    }

    private fun checkFielding(): Boolean {
        var status = false
        val name = binding.nameEditText.editText?.text.toString().trim()
        val lastName = binding.lastNameEditText.editText?.text.toString().trim()
        val direction = binding.directionMenu.editText?.text.toString().trim()
        val password = binding.passwordEditText.editText?.text.toString().trim()
        if (name.isEmpty()) {
            binding.nameEditText.error = "Ismingizni kiriting"
        } else {
            binding.nameEditText.error = null
        }
        if (lastName.isEmpty()) {
            binding.lastNameEditText.error = "Familiyangizni kiriting"
        } else {
            binding.lastNameEditText.error = null
        }
        if (direction.isEmpty()) {
            binding.directionMenu.error = "Yo'nalishni kiriting"
        } else {
            binding.directionMenu.error = null
        }
        if (password.isEmpty()) {
            binding.passwordEditText.error = "Parolni kiriting"
        } else {
            binding.passwordEditText.error = null
            status = true
        }
        return status
    }

    private fun sendUserData() {

        val name = binding.nameEditText.editText?.text.toString().trim()
        val lastName = binding.lastNameEditText.editText?.text.toString().trim()
        val direction = binding.directionMenu.editText?.text.toString().trim()
        val brandName = binding.brandName.editText?.text.toString().trim()
        val password = binding.passwordEditText.editText?.text.toString()
        val conformPassword = binding.conformPasswordEditText.editText?.text.toString()
        val bioText = binding.bioEditText.editText?.text.toString()
        val checkedRadioButtonId = binding.sexRadioGroup.checkedRadioButtonId
        val sex = if (checkedRadioButtonId == 1) "female" else "male"

        if (selectedLogoImageUri == null && selectedAvatarImageUri == null) {
            Snackbar.make(requireView(), "Select an Image First", Snackbar.LENGTH_LONG).show()
            return
        }

//        Avatar image upload
        val parcelAvatarFileDescriptor = selectedAvatarImageUri?.let {
            context?.contentResolver?.openFileDescriptor(
                it, "r", null
            )
        }

        val inputAvatarStream = parcelAvatarFileDescriptor?.let {
            FileInputStream(it.fileDescriptor)
        }

        val avatar = selectedAvatarImageUri?.let {
            File(
                requireContext().cacheDir,
                requireContext().contentResolver.getFileName(it)
            )
        }
        val outputAvatarStream = avatar?.let {
            FileOutputStream(avatar)
        }
        outputAvatarStream?.let { inputAvatarStream?.copyTo(it) }
        val avatarBody = avatar?.let {
            UploadImageRequestBody(it, "image", this)
        }

        val avatarImage = avatarBody?.let {
            MultipartBody.Part.createFormData(
                "avatar",
                avatar.name,
                it
            )
        }

//        Brand Logo upload
        val parcelBrandLogoFileDescriptor = selectedLogoImageUri?.let {
            context?.contentResolver?.openFileDescriptor(
                it, "r", null
            )
        }

        val inputBrandLogoStream = parcelBrandLogoFileDescriptor?.let {
            FileInputStream(it.fileDescriptor)
        }

        val brandLogo = selectedLogoImageUri?.let {
            File(requireContext().cacheDir, requireContext().contentResolver.getFileName(it))
        }

        val outputBrandLogoStream = brandLogo?.let {
            FileOutputStream(it)
        }
        outputBrandLogoStream?.let {
            inputBrandLogoStream?.copyTo(it)
        }

        val brandLogoBody = brandLogo?.let {
            UploadImageRequestBody(it, "image", this)
        }

        val brandLogoImage = brandLogoBody?.let {
            MultipartBody.Part.createFormData(
                "logo",
                brandLogo.name,
                it
            )
        }

        if (checkPassword() && checkFielding()) {
            signUpViewModel.setUserInfo(
                name,
                lastName,
                sex,
                direction,
                brandName,
                bioText,
                password,
                conformPassword,
                avatarImage,
                brandLogoImage
            ) { isSuccess ->
                if (isSuccess) {
//                    findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
                    Snackbar.make(
                        requireView(),
                        "Sizning ma'lumotlaringiz muvofaqiyatli saqlandi!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler().postDelayed({
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.flFragment, LoginFragment()).commit()
                    }, 3500)

                } else {
                    Snackbar.make(requireView(), "Xatolik yuz berdi", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun categoryDropDown() {
        val dataItems = listOf("Yo'nalishingizni tanlang")
        val adapter = DropdownAdapter(requireContext(), R.layout.dropdown_item, dataItems)
        binding.direction.setAdapter(adapter)
        binding.direction.setSelection(0)
        binding.directionMenu.editText?.ellipsize = TextUtils.TruncateAt.END

        categoryViewModel.getCategory()

        categoryViewModel.categoryListLiveData.observe(viewLifecycleOwner) {
            Log.d("checkCategoryFragment", "Fragment || categoryDropDown: $it")
            val adapter = DropdownAdapter(requireContext(), R.layout.dropdown_item, it)
            binding.direction.setAdapter(adapter)
            binding.direction.setSelection(0)
            binding.directionMenu.editText?.ellipsize = TextUtils.TruncateAt.END
        }
    }


    private fun getPhotoFromPhone() {

        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, 111)
        }
    }

    private fun getPhotoUri() {

        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, 112)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data?.data != null) {
            when (requestCode) {
                111 -> {
                    selectedAvatarImageUri = data.data
                    binding.posterImageView.setImageURI(selectedAvatarImageUri)
                }

                112 -> {
                    selectedLogoImageUri = data.data
                    binding.posterLogoImageView.setImageURI(selectedLogoImageUri)
                }
            }
        }
    }

    private fun View.snackbar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
        }.show()

    }
}

private fun ContentResolver.getFileName(selectedImageUri: Uri): String {
    var name = ""
    val returnCursor = this.query(selectedImageUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }

    return name
}