package uz.project.hunarbrand.profile.auth.signup.otpauth.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hunarbrend.signup.otpauth.viewModel.Category.CategoryViewModel
import uz.project.hunarbrand.profile.auth.signup.otpauth.viewModel.OTPViewModel
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentComformCodeBinding
import uz.project.hunarbrand.profile.auth.signup.otpauth.viewModel.ConformViewModel
import uz.project.hunarbrand.profile.auth.signup.send_user_info.ui.SignupFragment

class ConformCodeFragment(private val phoneNumber: String) :
    Fragment(R.layout.fragment_comform_code) {
    private val viewModel: ConformViewModel by viewModels()
    val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var countDownTimer: CountDownTimer
    private var _binding: FragmentComformCodeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComformCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        startCountdown()
        bindViewModel()
        binding.messageForUser.text =
            "Sizning +998${phoneNumber} raqamingizga sms kodi yuborildi. Iltimos ushbu kodni kiriting!"
    }

    private fun bindViewModel() {
        viewModel.conformCodeIsLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.conformCodeLoadingProgressContainer.visibility = View.VISIBLE
                binding.conformCodeScrollContainer.visibility = View.GONE
                binding.verifyButton.visibility = View.GONE
            } else {
                viewModel.conformCodeErrorMessage.observe(viewLifecycleOwner) { message ->
                    binding.messageForUser.text = message
                    binding.messageForUser.setTextColor(Color.RED)
                }
            }
        }
    }

    private fun setupViews() {
        binding.otpBox1.addTextChangedListener(createTextWatcher(binding.otpBox2))
        binding.otpBox2.addTextChangedListener(createTextWatcher(binding.otpBox3))
        binding.otpBox3.addTextChangedListener(createTextWatcher(binding.otpBox4))
        binding.otpBox4.addTextChangedListener(createTextWatcher(null))

//        resendOtpButton.setOnClickListener {
//            startCountdown()
//            resendCode()
//        }

        binding.verifyButton.setOnClickListener {
            val code =
                "${binding.otpBox1.text}" + "${binding.otpBox2.text}" + "${binding.otpBox3.text}" + "${binding.otpBox4.text}"
            viewModel.conformCode(code) { response ->
                when (response) {
                    true -> {
                        Log.d("navigatedFragment", "navigated: $response")
//                        findNavController().navigate(ConformCodeFragmentDirections.actionConformCodeFragmentToSignupFragment())
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.flFragment, SignupFragment()).commit()
                    }

                    false -> {
                        binding.conformCodeLoadingProgressContainer.visibility = View.GONE
                        binding.conformCodeScrollContainer.visibility = View.VISIBLE
                        binding.verifyButton.visibility = View.VISIBLE
                        viewModel.conformCodeErrorMessage.observe(viewLifecycleOwner) { message ->
                            binding.messageForUser.text = message
                            binding.messageForUser.setTextColor(Color.RED)
                        }
                    }
                }
            }
        }
    }

    fun errorCodeException() {

    }

    private fun createTextWatcher(nextBox: EditText?): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    nextBox?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        }
    }

    private fun startCountdown() {
        val durationInMillis: Long = 120000 // 2 minutes
        countDownTimer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val remainingTimeInSeconds = millisUntilFinished / 1000
                val minutes = millisUntilFinished / 60000
                val seconds: Long = millisUntilFinished % 60000 / 1000
                if (seconds < 10) binding.otpTimerTextView.text =
                    "Vaqt ${minutes}:0${seconds} qoldi"
                else binding.otpTimerTextView.text = "Vaqt ${minutes}:${seconds} qoldi"
            }

            override fun onFinish() {
                binding.messageForUser.text = "Kod vaqt tugadi"
                binding.messageForUser.setTextColor(Color.RED)
                binding.otpTimerTextView.text = "0:00"
                binding.otpTimerTextView.setTextColor(Color.RED)
            }
        }

        countDownTimer.start()
    }

//    private fun resendCode() {
//        viewModel.getCode(phoneNumber)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer.cancel()
        viewModel.response.removeObservers(viewLifecycleOwner)
    }
}