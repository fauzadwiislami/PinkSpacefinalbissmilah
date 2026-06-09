package com.example.pinkspace

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.pinkspace.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    private var selectedAvatarName: String = "avatar_1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadUserProfile()

        binding.btnChangeAvatar.setOnClickListener {
            showAvatarPicker()
        }

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadUserProfile() {
        val user = auth.currentUser

        if (user == null) {
            Toast.makeText(requireContext(), "User belum login", Toast.LENGTH_SHORT).show()
            return
        }

        binding.txtEmail.text = user.email ?: "No email"

        database.reference
            .child("users")
            .child(user.uid)
            .get()
            .addOnSuccessListener { snapshot ->
                val fullName = snapshot.child("fullName").getValue(String::class.java)
                val username = snapshot.child("username").getValue(String::class.java)
                val email = snapshot.child("email").getValue(String::class.java)
                val avatarName = snapshot.child("avatarName").getValue(String::class.java) ?: "avatar_1"

                val averageCycleLength = snapshot.child("averageCycleLength").getValue(Int::class.java)
                    ?: snapshot.child("averageCycleLength").getValue(Long::class.java)?.toInt()
                    ?: 28

                val averagePeriodLength = snapshot.child("averagePeriodLength").getValue(Int::class.java)
                    ?: snapshot.child("averagePeriodLength").getValue(Long::class.java)?.toInt()
                    ?: 5

                val finalUsername = username ?: fullName ?: "PinkSpace User"

                selectedAvatarName = avatarName

                binding.txtUsername.text = finalUsername
                binding.txtEmail.text = email ?: user.email ?: "No email"

                binding.txtCycleLength.text = "$averageCycleLength days"
                binding.txtPeriodLength.text = "$averagePeriodLength days"

                binding.edtUsername.setText(finalUsername)
                binding.edtCycleLength.setText(averageCycleLength.toString())
                binding.edtPeriodLength.setText(averagePeriodLength.toString())

                setAvatarImage(selectedAvatarName)
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    requireContext(),
                    "Gagal mengambil profile: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun showAvatarPicker() {
        val avatarLabels = arrayOf(
            "Pink Avatar",
            "Purple Avatar",
            "Yellow Avatar"
        )

        val avatarValues = arrayOf(
            "avatar_1",
            "avatar_2",
            "avatar_3"
        )

        AlertDialog.Builder(requireContext())
            .setTitle("Choose Avatar")
            .setItems(avatarLabels) { _, which ->
                selectedAvatarName = avatarValues[which]
                setAvatarImage(selectedAvatarName)
            }
            .show()
    }

    private fun setAvatarImage(avatarName: String) {
        when (avatarName) {
            "avatar_1" -> binding.imgProfile.setImageResource(R.drawable.avatar_1)
            "avatar_2" -> binding.imgProfile.setImageResource(R.drawable.avatar_2)
            "avatar_3" -> binding.imgProfile.setImageResource(R.drawable.avatar_3)
            else -> binding.imgProfile.setImageResource(R.drawable.avatar_1)
        }
    }

    private fun saveProfile() {
        val user = auth.currentUser

        if (user == null) {
            Toast.makeText(requireContext(), "User belum login", Toast.LENGTH_SHORT).show()
            return
        }

        val username = binding.edtUsername.text.toString().trim()
        val cycleLength = binding.edtCycleLength.text.toString().trim().toIntOrNull()
        val periodLength = binding.edtPeriodLength.text.toString().trim().toIntOrNull()

        if (username.isEmpty()) {
            binding.usernameLayout.error = "Username wajib diisi"
            return
        } else {
            binding.usernameLayout.error = null
        }

        if (cycleLength == null || cycleLength < 20 || cycleLength > 45) {
            binding.cycleLengthLayout.error = "Cycle length harus 20–45 hari"
            return
        } else {
            binding.cycleLengthLayout.error = null
        }

        if (periodLength == null || periodLength < 1 || periodLength > 10) {
            binding.periodLengthLayout.error = "Period length harus 1–10 hari"
            return
        } else {
            binding.periodLengthLayout.error = null
        }

        binding.btnSaveProfile.isEnabled = false
        binding.btnSaveProfile.text = "Saving..."

        val updatedData = mapOf<String, Any>(
            "username" to username,
            "avatarName" to selectedAvatarName,
            "averageCycleLength" to cycleLength,
            "averagePeriodLength" to periodLength,
            "updatedAt" to System.currentTimeMillis()
        )

        database.reference
            .child("users")
            .child(user.uid)
            .updateChildren(updatedData)
            .addOnSuccessListener {
                binding.btnSaveProfile.isEnabled = true
                binding.btnSaveProfile.text = "Save Changes"

                binding.txtUsername.text = username
                binding.txtCycleLength.text = "$cycleLength days"
                binding.txtPeriodLength.text = "$periodLength days"
                setAvatarImage(selectedAvatarName)

                Toast.makeText(
                    requireContext(),
                    "Profile berhasil diperbarui",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { error ->
                binding.btnSaveProfile.isEnabled = true
                binding.btnSaveProfile.text = "Save Changes"

                Toast.makeText(
                    requireContext(),
                    "Gagal update profile: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun logoutUser() {
        auth.signOut()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}