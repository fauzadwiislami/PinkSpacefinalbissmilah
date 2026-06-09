package com.example.pinkspace

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pinkspace.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kalau user sudah login, langsung masuk ke halaman utama
        if (auth.currentUser != null) {
            goToMain()
            return
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.txtCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.emailLayout.error = "Email wajib diisi"
            return
        } else {
            binding.emailLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password wajib diisi"
            return
        } else {
            binding.passwordLayout.error = null
        }

        binding.btnLogin.isEnabled = false
        binding.btnLogin.text = "Loading..."

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                goToMain()
            }
            .addOnFailureListener { error ->
                binding.btnLogin.isEnabled = true
                binding.btnLogin.text = "Login"

                Toast.makeText(
                    this,
                    "Login gagal: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}