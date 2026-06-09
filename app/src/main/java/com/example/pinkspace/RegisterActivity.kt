package com.example.pinkspace

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.pinkspace.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            validateAndGoToTerms()
        }

        binding.txtGoLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateAndGoToTerms() {
        val fullName = binding.edtFullName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val username = binding.edtUsername.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (fullName.isEmpty()) {
            binding.fullNameLayout.error = "Full name wajib diisi"
            return
        } else {
            binding.fullNameLayout.error = null
        }

        if (email.isEmpty()) {
            binding.emailLayout.error = "Email wajib diisi"
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = "Format email tidak valid"
            return
        } else {
            binding.emailLayout.error = null
        }

        if (username.isEmpty()) {
            binding.usernameLayout.error = "Username wajib diisi"
            return
        } else if (username.length < 3) {
            binding.usernameLayout.error = "Username minimal 3 karakter"
            return
        } else {
            binding.usernameLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password wajib diisi"
            return
        } else if (password.length < 6) {
            binding.passwordLayout.error = "Password minimal 6 karakter"
            return
        } else {
            binding.passwordLayout.error = null
        }

        val intent = Intent(this, TermsActivity::class.java)
        intent.putExtra("fullName", fullName)
        intent.putExtra("email", email)
        intent.putExtra("username", username)
        intent.putExtra("password", password)
        startActivity(intent)
    }
}