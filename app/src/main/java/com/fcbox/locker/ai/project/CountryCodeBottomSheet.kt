package com.fcbox.locker.ai.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcbox.locker.ai.project.databinding.DialogCountryCodePickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

data class CountryCode(val name: String, val code: String, val flag: String)

class CountryCodeBottomSheet(private val onCodeSelected: (CountryCode) -> Unit) : BottomSheetDialogFragment() {

    private var _binding: DialogCountryCodePickerBinding? = null
    private val binding get() = _binding!!

    private val countryCodes = listOf(
        CountryCode("中国", "+86", "🇨🇳"),
        CountryCode("香港", "+852", "🇭🇰"),
        CountryCode("澳门", "+853", "🇲🇴"),
        CountryCode("台湾", "+886", "🇹🇼"),
        CountryCode("美国", "+1", "🇺🇸"),
        CountryCode("英国", "+44", "🇬🇧"),
        CountryCode("日本", "+81", "🇯🇵"),
        CountryCode("韩国", "+82", "🇰🇷")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCountryCodePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val displayValues = countryCodes.map { "${it.flag} ${it.name} (${it.code})" }.toTypedArray()

        binding.numberPicker.apply {
            minValue = 0
            maxValue = countryCodes.size - 1
            displayedValues = displayValues
            wrapSelectorWheel = false
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvConfirm.setOnClickListener {
            onCodeSelected(countryCodes[binding.numberPicker.value])
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
