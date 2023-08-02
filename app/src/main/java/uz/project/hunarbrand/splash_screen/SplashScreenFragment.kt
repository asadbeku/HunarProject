package uz.project.hunarbrand.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uz.project.hunarbrand.main_app.FragmentMain
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentSplashScreenBinding
import uz.project.hunarbrand.splash_screen.view_model.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        viewModel.getCategories()
        viewModel.getProfileInfo()
        viewModel.getSearchItems()

        Handler().postDelayed({
            parentFragmentManager.beginTransaction()
                .replace(R.id.fullContainer, FragmentMain())
                .commit()

        }, 5000)

        binding.versionTextView.text = "Version: ${
            context?.packageManager?.getPackageInfo(
                requireContext().packageName,
                0
            )?.versionName
        }"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }
}