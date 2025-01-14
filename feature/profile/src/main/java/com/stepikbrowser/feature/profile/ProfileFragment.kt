package com.stepikbrowser.feature.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.stepikbrowser.feature.profile.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.profile_fragment) {
    private lateinit var binding: ProfileFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragmentBinding.bind(view)

    }
}