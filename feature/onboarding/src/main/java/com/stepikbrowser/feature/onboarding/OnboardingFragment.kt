package com.stepikbrowser.feature.onboarding

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.stepikbrowser.feature.onboarding.databinding.OnboardingBinding

class OnboardingFragment: Fragment(R.layout.onboarding) {
    private lateinit var binding: OnboardingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = OnboardingBinding.bind(view)

        //Continuing from Onboarding To Auth
        binding.continueButton.setOnClickListener {
            val deepLinkRequest = NavDeepLinkRequest.Builder
                .fromUri("stepikbrowser://auth".toUri())
                .build()
            if (isAdded)
                findNavController().navigate(deepLinkRequest)
        }
    }
}