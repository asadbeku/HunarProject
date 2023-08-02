package uz.project.hunarbrand.profile.auth.signup.otpauth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uz.project.hunarbrand.profile.auth.signup.otpauth.viewModel.OTPViewModel
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentSendCodeBinding
import uz.project.hunarbrand.profile.auth.login.ui.LoginFragment

class SendCodeFragment : Fragment(R.layout.fragment_send_code) {
    private val viewModel: OTPViewModel by viewModels()
    private var _binding: FragmentSendCodeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        binding.loginButton.setOnClickListener {
            if (binding.phoneNumberEditText.editText?.text.toString().length == 9) {
                viewModel.getCode(binding.phoneNumberEditText.editText?.text.toString())
            } else {
                binding.phoneNumberEditText.error = "Invalid phone number"
            }
        }

        binding.alreadyHaveAccountTextView.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.flFragment,
                LoginFragment()
            ).commit()
//            findNavController().navigate(SendCodeFragmentDirections.actionSendCodeFragmentToLoginFragment())
        }
    }

    private fun bindViewModel() {
        viewModel.sendCodeIsLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.sendCodeLoadingProgressContainer.visibility = View.VISIBLE
                binding.sendCodeScrollContainer.visibility = View.GONE
                binding.loginButton.visibility = View.GONE
            } else {
                viewModel.sendCodeErrorMessage.observe(viewLifecycleOwner) { message ->
                    binding.phoneNumberEditText.error = message
                }

                binding.sendCodeLoadingProgressContainer.visibility = View.GONE
                binding.sendCodeScrollContainer.visibility = View.VISIBLE
                binding.loginButton.visibility = View.VISIBLE
            }
        }

        viewModel.tokenLiveData.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                parentFragmentManager.beginTransaction().replace(
                    R.id.flFragment,
                    ConformCodeFragment(binding.phoneNumberEditText.editText?.text.toString())
                ).commit()
//                findNavController().navigate(SendCodeFragmentDirections.actionSendCodeFragmentToConformCodeFragment(phoneNumberEditText.editText?.text.toString()))
            }
        }

    }

}