package uz.project.hunarbrand.profile.auth.login.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.DecodedJWT
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentLoginBinding
import uz.project.hunarbrand.profile.auth.login.view_model.LoginViewModel
import uz.project.hunarbrand.profile.auth.signup.otpauth.ui.SendCodeFragment
import uz.project.hunarbrand.profile.profile_info.ui.ProfileFragment
import uz.project.hunarbrand.utils.Bearer

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TTTcheck", "onViewCreated: LoginFragment")

        login()

        binding.registrationTextView.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.flFragment, SendCodeFragment())
                .commit()
        }

    }

    private fun login() {
        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.phoneNumberEditText.editText?.text.toString(),
                binding.passwordEditText.editText?.text.toString()
            ) {
                binding.passwordEditText.error = it
                binding.phoneNumberEditText.error = it
            }
        }
        bindViewModel()
        viewModel.updatingState.observe(viewLifecycleOwner, ::updatingState)
    }

    private fun bindViewModel() {
        viewModel.login.observe(viewLifecycleOwner) {
            val user = it
            if (user != null) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, ProfileFragment())
                    .commit()
            }
        }
    }

    private fun updatingState(isLoading: Boolean) {
        binding.updatingContainer.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.mainContainer.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

}