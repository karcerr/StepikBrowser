package com.stepikbrowser.feature.auth

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.stepikbrowser.feature.auth.databinding.AuthBinding


@AndroidEntryPoint
class AuthenticationFragment: Fragment(R.layout.auth) {
    private val viewModel: AuthenticationFragmentViewModel by viewModels()
    private lateinit var binding: AuthBinding

    private var isRegisterMode = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AuthBinding.bind(view)


        setClickableText()
        binding.authButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val repeatPassword = if (isRegisterMode) binding.repeatPasswordEditText.text.toString() else null
            binding.errorTextView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.authenticate(email, password, repeatPassword, isRegisterMode)
        }

        viewModel.errorState.observe(viewLifecycleOwner) { error ->
            error?.let { showErrorMessage(it) }
        }

        viewModel.authStatus.observe(viewLifecycleOwner) { state ->
            Log.d("AuthLogger", state.toString())
            if (!state) {
                showErrorMessage(requireContext().getString(R.string.unknown_auth_error))
                return@observe
            }

            val deepLinkRequest = NavDeepLinkRequest.Builder
                .fromUri("stepikbrowser://main_graph".toUri())
                .build()
            findNavController().popBackStack()
            findNavController().navigate(deepLinkRequest)
        }
    }

    private fun showErrorMessage(error: String) {
        binding.progressBar.visibility = View.GONE
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = error
    }
    private fun setClickableText() {
        val fullText =
            if (isRegisterMode)
                getString(R.string.already_have_an_account)
            else getString(R.string.dont_have_an_account)
        // There could be many locales, but they all have '?' in common, the clickable part of the text is always after it
        val questionMarkIndex = fullText.indexOf('?') + 1
        val spannableString = SpannableString(fullText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                changeRegisterMode()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.BLUE
            }
        }
        spannableString.setSpan(clickableSpan, questionMarkIndex + 1, fullText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.hyperlinkText.text = spannableString
        binding.hyperlinkText.movementMethod = LinkMovementMethod.getInstance()
    }
    private fun changeRegisterMode() {
        isRegisterMode = !isRegisterMode
        binding.repeatPasswordEditText.visibility = if (isRegisterMode) View.VISIBLE else View.GONE
        binding.errorTextView.visibility = View.GONE
        binding.authButton.text = if (isRegisterMode) getString(R.string.register) else getString(R.string.log_in)
        binding.authTitle.text = if (isRegisterMode) getString(R.string.register_title) else getString(R.string.log_in_title)
        setClickableText()
    }
}