package uz.project.hunarbrand.profile.profile_info.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentProfileBinding
import uz.project.hunarbrand.profile.profile_info.adapter.tab_layout.ProfileTabLayoutAdapter
import uz.project.hunarbrand.profile.profile_info.view_models.profile.ProfileViewModel

class ProfileFragment() : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TTTcheck", "onViewCreated: ProfileFragment")
        bindUserInfoObserver()
        bindTabLayout()
    }

    private fun bindTabLayout() {
        val adapter = ProfileTabLayoutAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Brand maxsulotlarim"
                1 -> tab.text = "Eksklyuziv maxsulotlarim"
            }
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun bindUserInfoObserver() {

        viewModel.getProfileInfo(){
            if (it.directionUz == "null"){
                binding.userName.text = "Foydalanuvchi"
            }else{
                binding.userName.text = it.directionUz
            }

            Log.d("userInfo", "bindUserInfoObserver: $it")

            binding.fullName.text = "${it.firstName} ${it.lastName}"
            binding.bioText.text = it.bioUz

            Glide.with(requireContext())
                .load(it.avatar)
                .placeholder(R.drawable.avatar_male)
                .centerCrop()
                .into(binding.profileImageView)
        }

        viewModel.updatingState.observe(viewLifecycleOwner){
            if (it){
                binding.motionLayout.visibility = View.INVISIBLE
                binding.progress.visibility = View.VISIBLE
            }else{
                binding.motionLayout.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE
            }

        }
    }


}