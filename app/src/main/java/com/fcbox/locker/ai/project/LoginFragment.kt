package com.fcbox.locker.ai.project

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fcbox.locker.ai.project.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    
    private var countDownTimer: CountDownTimer? = null

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
        initListeners()
    }

    private fun initListeners() {
        // 国家码选择
        binding.tvCountryCode.setOnClickListener {
            CountryCodeBottomSheet { selected ->
                binding.tvCountryCode.text = "${selected.flag} ${selected.code}"
            }.show(childFragmentManager, "CountryCodePicker")
        }

        // 扩大勾选范围
        binding.llAgreementContainer.setOnClickListener {
            binding.cbAgreement.isChecked = !binding.cbAgreement.isChecked
        }

        // 协议链接点击
        binding.tvAgreementLink.setOnClickListener {
            val bundle = bundleOf("url" to "https://www.baidu.com")
            findNavController().navigate(R.id.action_loginFragment_to_webViewFragment, bundle)
        }

        // 获取验证码点击
        binding.tvGetCode.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            if (phone.length < 5) {
                Toast.makeText(requireContext(), getString(R.string.toast_phone_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            startCountDown()
            Toast.makeText(requireContext(), getString(R.string.get_code), Toast.LENGTH_SHORT).show()
        }

        // 登录按钮点击
        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            val code = binding.etCode.text.toString().trim()

            if (phone.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.toast_phone_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (code.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.toast_code_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.cbAgreement.isChecked) {
                Toast.makeText(requireContext(), getString(R.string.toast_agreement_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 登录成功，缓存状态并跳转到首页
            UserStorage.setLoggedIn(requireContext(), true)
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    private fun startCountDown() {
        binding.tvGetCode.isEnabled = false
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvGetCode.text = getString(R.string.seconds_suffix, secondsRemaining)
            }

            override fun onFinish() {
                binding.tvGetCode.isEnabled = true
                binding.tvGetCode.text = getString(R.string.get_code)
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }
}
