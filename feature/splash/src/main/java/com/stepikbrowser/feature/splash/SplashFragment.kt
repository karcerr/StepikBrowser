package com.stepikbrowser.feature.splash

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: Fragment(R.layout.fragment_splash) {
    private val viewModel: SplashFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            val deepLinkUri = if (isLoggedIn) {
                "stepikbrowser://main_graph"
            } else {
                "stepikbrowser://onboarding_and_auth"
            }

            val deepLinkRequest = NavDeepLinkRequest.Builder
                .fromUri(deepLinkUri.toUri())
                .build()

            findNavController().navigate(deepLinkRequest)
        }

    }
}