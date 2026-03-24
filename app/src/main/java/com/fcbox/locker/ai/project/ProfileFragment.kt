package com.fcbox.locker.ai.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fcbox.locker.ai.project.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "ProfileFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        // 使用资源 ID，系统会自动处理国际化切换
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_logout_title)
            .setMessage(R.string.dialog_logout_message)
            .setPositiveButton(R.string.dialog_yes) { _, _ ->
                performLogout()
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun performLogout() {
        Log.d(TAG, "Starting logout process...")
        // 1. 清空登录态
        UserStorage.setLoggedIn(requireContext(), false)
        
        // 2. 执行跳转：根据日志结论，只有通过父级 MainFragment 才能找到正确的全局控制器
        val rootNavController = parentFragment?.parentFragment?.findNavController()
        
        if (rootNavController != null) {
            Log.d(TAG, "Navigating to login...")
            rootNavController.navigate(R.id.action_mainFragment_to_loginFragment)
        } else {
            Log.e(TAG, "Navigation failed: Could not find root NavController via parent hierarchy")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
