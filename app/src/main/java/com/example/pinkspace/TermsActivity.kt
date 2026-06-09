package com.example.pinkspace

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pinkspace.databinding.ActivityTermsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TermsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTermsBinding

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    private var fullName: String = ""
    private var email: String = ""
    private var username: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fullName = intent.getStringExtra("fullName") ?: ""
        email = intent.getStringExtra("email") ?: ""
        username = intent.getStringExtra("username") ?: ""
        password = intent.getStringExtra("password") ?: ""

        setupInitialState()
        setupCheckboxListener()
        setupCreateAccountButton()
    }

    private fun setupInitialState() {
        binding.checkAgree.isEnabled = true
        binding.checkAgree.isChecked = false

        binding.btnCreateAccount.isEnabled = false
        binding.btnCreateAccount.alpha = 0.6f
    }

    private fun setupCheckboxListener() {
        binding.checkAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.btnCreateAccount.isEnabled = isChecked
            binding.btnCreateAccount.alpha = if (isChecked) 1.0f else 0.6f
        }
    }

    private fun setupCreateAccountButton() {
        binding.btnCreateAccount.setOnClickListener {
            if (!binding.checkAgree.isChecked) {
                Toast.makeText(
                    this,
                    "Centang persetujuan dulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            createAccount()
        }
    }

    private fun createAccount() {
        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                "Data registrasi tidak lengkap",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        binding.btnCreateAccount.isEnabled = false
        binding.btnCreateAccount.text = "CREATING..."

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userId = auth.currentUser?.uid

                if (userId == null) {
                    resetCreateButton()
                    Toast.makeText(this, "User ID tidak ditemukan", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val userData = mapOf(
                    "userId" to userId,
                    "fullName" to fullName,
                    "email" to email,
                    "username" to username,
                    "avatarName" to "avatar_1",
                    "averageCycleLength" to 28,
                    "averagePeriodLength" to 5,
                    "termsAccepted" to true,
                    "createdAt" to System.currentTimeMillis()
                )

                database.reference
                    .child("users")
                    .child(userId)
                    .setValue(userData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Akun berhasil dibuat. Silakan login.",
                            Toast.LENGTH_LONG
                        ).show()

                        auth.signOut()

                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { error ->
                        resetCreateButton()

                        Toast.makeText(
                            this,
                            "Akun dibuat, tapi gagal simpan database: ${error.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            .addOnFailureListener { error ->
                resetCreateButton()

                Toast.makeText(
                    this,
                    "Register gagal: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun resetCreateButton() {
        binding.btnCreateAccount.text = "CREATE ACCOUNT"
        binding.btnCreateAccount.isEnabled = binding.checkAgree.isChecked
        binding.btnCreateAccount.alpha = if (binding.checkAgree.isChecked) 1.0f else 0.6f
    }
}