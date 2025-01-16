package com.stepikbrowser.feature.onboarding

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.stepikbrowser.feature.onboarding.databinding.OnboardingBinding
import eightbitlab.com.blurview.BlurView

class OnboardingFragment: Fragment(R.layout.onboarding) {
    private lateinit var binding: OnboardingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = OnboardingBinding.bind(view)
        val chipList = getLocalizedChipNames(requireContext())
        createChips(chipList)
        binding.continueButton.setOnClickListener {
            val deepLinkRequest = NavDeepLinkRequest.Builder
                .fromUri("stepikbrowser://auth".toUri())
                .build()
            findNavController().popBackStack()
            findNavController().navigate(deepLinkRequest)
        }
    }
    private fun createChips(stringList: List<String>) {
        val radius = 16f
        val rootView = binding.flexboxLayout
        val lightgrayColor = ContextCompat.getColor(requireContext(), com.stepikbrowser.core.ui.R.color.light_gray_glass)
        stringList.forEach { string ->
            val textView = TextView(requireContext()).apply {
                text = string
                textSize = 25f
                setTypeface(typeface, Typeface.BOLD)
                setTextAppearance(com.stepikbrowser.core.ui.R.style.TitleMedium)
                isClickable = true
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                background = null
            }

            val blurView = BlurView(requireContext())
            blurView.addView(textView)
            blurView.apply {
                setupWith(rootView)
                    .setBlurRadius(radius)
                    .setBlurAutoUpdate(true)
                setBlurAutoUpdate(true)
                setOverlayColor(lightgrayColor)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(5.dpToPx(context), 5.dpToPx(context), 5.dpToPx(context), 5.dpToPx(context))
                    setPadding(15.dpToPx(context), 15.dpToPx(context), 15.dpToPx(context), 15.dpToPx(context))
                }
                background = ContextCompat.getDrawable(context, com.stepikbrowser.core.ui.R.drawable.round_corners)
                outlineProvider = ViewOutlineProvider.BACKGROUND
                clipToOutline = true
            }

            binding.flexboxLayout.addView(blurView)

            textView.setOnClickListener {
                blurView.setOverlayColor(ContextCompat.getColor(requireContext(), com.stepikbrowser.core.ui.R.color.green))
            }
        }
    }

    private fun getLocalizedChipNames(context: Context): List<String> {
        val resourceFields = R.string::class.java.declaredFields
        val chipNames = mutableListOf<String>()

        resourceFields.forEach { field ->
            if (field.name.startsWith("chip_")) {
                try {
                    val resId = field.getInt(null)
                    chipNames.add(context.getString(resId))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return chipNames
    }
}

fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}