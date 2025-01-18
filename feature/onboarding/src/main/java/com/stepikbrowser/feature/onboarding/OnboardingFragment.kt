package com.stepikbrowser.feature.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.stepikbrowser.feature.onboarding.databinding.OnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.BlurView

@AndroidEntryPoint
class OnboardingFragment: Fragment(R.layout.onboarding) {
    private lateinit var binding: OnboardingBinding
    private val viewModel: OnboardingFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = OnboardingBinding.bind(view)

        viewModel.chipList.observe(viewLifecycleOwner) {
            createChips(it)
        }

        binding.continueButton.setOnClickListener {
            val deepLinkRequest = NavDeepLinkRequest.Builder
                .fromUri("stepikbrowser://auth".toUri())
                .build()
            findNavController().popBackStack()
            findNavController().navigate(deepLinkRequest)
        }
    }

    private fun createChips(chipList: List<MyChip>) {
        val radius = 16f
        val rootView = binding.flexboxLayout
        val lightgrayColor = ContextCompat.getColor(requireContext(), com.stepikbrowser.core.ui.R.color.light_gray_glass)
        chipList.forEach { chip ->
            val textView = TextView(requireContext()).apply {
                text = chip.text
                textSize = 25f
                setTypeface(typeface, Typeface.BOLD)
                setTextAppearance(com.stepikbrowser.core.ui.R.style.TitleMedium)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                background = null
            }

            val blurView = BlurView(requireContext())
            blurView.addView(textView)
            blurView.apply {
                background = ContextCompat.getDrawable(context, com.stepikbrowser.core.ui.R.drawable.round_corners)
                outlineProvider = ViewOutlineProvider.BACKGROUND
                setupWith(rootView)
                setBlurEnabled(true)
                setBlurRadius(radius)
                setBlurAutoUpdate(true)
                setOverlayColor(lightgrayColor)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(5.dpToPx(context), 5.dpToPx(context), 5.dpToPx(context), 5.dpToPx(context))
                    setPadding(15.dpToPx(context), 15.dpToPx(context), 15.dpToPx(context), 15.dpToPx(context))
                }
                isClickable = true
                clipToOutline = true
            }

            binding.flexboxLayout.addView(blurView)

            blurView.setOnClickListener {
                val isGoingUp = chipList.indexOf(chip) >= chipList.size / 2
                val rotationAngle = if (chip.isClicked) 0f else if (isGoingUp) -30f else 30f
                val translationY = if (chip.isClicked) 0f else if (isGoingUp) -50f else 50f
                val overlayColor = if (chip.isClicked) lightgrayColor else ContextCompat.getColor(requireContext(), com.stepikbrowser.core.ui.R.color.green)
                blurView.setOverlayColor(overlayColor)

                chip.isClicked = !chip.isClicked
                val rotationAnimator = ObjectAnimator.ofFloat(blurView, "rotation", blurView.rotation, rotationAngle)
                val translationYAnimator = ObjectAnimator.ofFloat(blurView, "translationY", blurView.translationY, translationY)
                AnimatorSet().apply {
                    playTogether(rotationAnimator, translationYAnimator)
                    duration = 500
                    start()
                }
            }
        }
    }
    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

}

