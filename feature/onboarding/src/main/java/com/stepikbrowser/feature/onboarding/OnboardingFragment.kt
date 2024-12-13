package com.stepikbrowser.feature.onboarding

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController

class OnboardingFragment: Fragment(R.layout.onboarding) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Continuing from Onboarding To Auth
        view.findViewById<Button>(R.id.continue_button).setOnClickListener {
            val deepLinkRequest = NavDeepLinkRequest.Builder
                .fromUri("stepikbrowser://auth".toUri())
                .build()

            findNavController().navigate(deepLinkRequest)
        }
    }
}