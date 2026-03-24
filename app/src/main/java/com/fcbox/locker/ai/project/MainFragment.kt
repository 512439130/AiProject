package com.fcbox.locker.ai.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fcbox.locker.ai.project.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Handle window insets to avoid overlap with navigation bar
        // Apply padding to the root view so the entire bottom area is lifted
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainRoot) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_main_container) as NavHostFragment
        val navController = navHostFragment.navController
        
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
