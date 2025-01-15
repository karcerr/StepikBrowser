package com.stepikbrowser.feature.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.stepikbrowser.feature.profile.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.profile_fragment) {
    private lateinit var binding: ProfileFragmentBinding
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragmentBinding.bind(view)
        setupObservers()
        binding.logout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun setupObservers() {
        viewModel.email.observe(viewLifecycleOwner) {
            binding.email.text = it.toString()
        }
        viewModel.authStatus.observe(viewLifecycleOwner) { authenticated ->
            when (authenticated) {
                false -> {
                    val deepLinkRequest = NavDeepLinkRequest.Builder
                        .fromUri("stepikbrowser://onboarding_and_auth".toUri())
                        .build()

                    clearBackStack()
                    findNavController().navigate(deepLinkRequest)
                    parentFragmentManager.beginTransaction().remove(this).commit();
                }

                true -> {}
            }
        }
    }
    private fun clearBackStack() {
        val navController = findNavController()
        while (navController.popBackStack()) {
            // I'm clearing backstack here so it doesn't go back to main body if you press back after logging out
            // If you question this decision I will find you
        }
    }
}