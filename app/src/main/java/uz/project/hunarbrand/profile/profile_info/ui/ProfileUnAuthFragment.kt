package uz.project.hunarbrand.profile.profile_info.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentProfileUnauthBinding
import uz.project.hunarbrand.profile.auth.login.ui.LoginFragment
import uz.project.hunarbrand.profile.auth.signup.otpauth.ui.SendCodeFragment

class ProfileUnAuthFragment : Fragment(R.layout.fragment_profile_unauth) {

    private var _binding: FragmentProfileUnauthBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileUnauthBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TTTcheck", "onViewCreated: ProfileFragment")
        binding.loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.flFragment, LoginFragment())
                .commit()
        }

        binding.RegisterButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.flFragment, SendCodeFragment())
                .commit()
        }
    }

}