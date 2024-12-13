package com.stepikbrowser.feature.auth

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment: Fragment(R.layout.auth) {
    private val viewModel: AuthenticationFragmentViewModel by viewModels()
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var errorTextView: TextView
    private lateinit var authButton: Button
    private lateinit var progressBar: ProgressBar
    private var isRegisterMode = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.email_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        repeatPasswordEditText = view.findViewById(R.id.repeat_password_edit_text)
        errorTextView = view.findViewById(R.id.error_text_view)
        authButton = view.findViewById(R.id.auth_button)
        progressBar = view.findViewById(R.id.progress_bar)

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
            Toast.makeText(context, state.toString(), Toast.LENGTH_LONG).show()
            //TODO: navigation to main nested graph
        })
    }

    private fun showErrorMessage(error: String) {
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = error
    }
}