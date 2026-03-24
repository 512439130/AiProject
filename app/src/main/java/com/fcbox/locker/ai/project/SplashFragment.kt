package com.fcbox.locker.ai.project

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.fcbox.locker.ai.project.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "SplashFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startConfigurationUpdate()
    }

    private fun startConfigurationUpdate() {
        Log.d(TAG, "Starting config update...")
        binding.tvStatus.setText(R.string.splash_status_init)
        
        Handler(Looper.getMainLooper()).postDelayed({
            // 安全检查：Fragment 是否还挂载在 Activity 上
            if (isAdded && _binding != null) {
                binding.tvStatus.setText(R.string.splash_status_done)
                navigateToNext()
            }
        }, 2000)
    }

    private fun navigateToNext() {
        try {
            val context = context ?: return
            Log.d(TAG, "Preparing navigation...")
            
            // 使用更稳健的跳转配置
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true) // 彻底清理整个导航栈，以新页面为根
                .build()

            val isLoggedIn = UserStorage.isLoggedIn(context)
            Log.d(TAG, "Is user logged in: $isLoggedIn")

            if (isLoggedIn) {
                findNavController().navigate(R.id.mainFragment, null, navOptions)
            } else {
                findNavController().navigate(R.id.loginFragment, null, navOptions)
            }
            Log.d(TAG, "Navigation executed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Navigation failed: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
