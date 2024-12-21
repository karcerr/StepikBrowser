package com.stepikbrowser.feature.auth

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class AuthenticationFragment: Fragment(R.layout.auth) {
    private val viewModel: AuthenticationFragmentViewModel by viewModels()
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var errorTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var authButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var linkTextView: TextView
    private var isRegisterMode = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.email_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        repeatPasswordEditText = view.findViewById(R.id.repeat_password_edit_text)
        errorTextView = view.findViewById(R.id.error_text_view)
        titleTextView = view.findViewById(R.id.auth_title)
        authButton = view.findViewById(R.id.auth_button)
        progressBar = view.findViewById(R.id.progress_bar)
        linkTextView = view.findViewById(R.id.hyperlink_text)

        setClickableText()
        authButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val repeatPassword = if (isRegisterMode) repeatPasswordEditText.text.toString() else null
            errorTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.authenticate(email, password, repeatPassword, isRegisterMode)
        }

        viewModel.errorState.observe(viewLifecycleOwner, { error ->
            error?.let { showErrorMessage(it) }
        })

        viewModel.authStatus.observe(viewLifecycleOwner, { state ->
            Log.d("AuthLogger", state.toString())
            if (!state) {
                showErrorMessage(getString(R.string.unknown_auth_error))
                return@observe
            }

            val deepLinkRequest = NavDeepLinkRequest.Builder
                .fromUri("stepikbrowser://main_graph".toUri())
                .build()
            if (isAdded)
                findNavController().navigate(deepLinkRequest)
        })
    }

    private fun showErrorMessage(error: String) {
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = error
    }
    private fun setClickableText() {
        val fullText =
            if (isRegisterMode)
                getString(R.string.already_have_an_account)
            else getString(R.string.dont_have_an_account)
        // There could be many locales, but they all have '?' in common, the clickable part of the text is always after it
        val questionMarkIndex = fullText.indexOf('?')
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

        linkTextView.text = spannableString
        linkTextView.movementMethod = LinkMovementMethod.getInstance()
    }
    private fun changeRegisterMode() {
        isRegisterMode = !isRegisterMode
        repeatPasswordEditText.visibility = if (isRegisterMode) View.VISIBLE else View.GONE
        authButton.text = if (isRegisterMode) getString(R.string.register) else getString(R.string.log_in)
        titleTextView.text = if (isRegisterMode) getString(R.string.register_title) else getString(R.string.log_in_title)
        setClickableText()
    }
}