package com.example.pinkspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pinkspace.databinding.FragmentLogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LogFragment : Fragment() {

    private var _binding: FragmentLogBinding? = null
    private val binding get() = _binding!!

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDropdowns()

        binding.btnSaveLog.setOnClickListener {
            saveDailyLog()
        }
    }

    private fun setupDropdowns() {
        val moods = listOf("Happy", "Calm", "Tired", "Sad", "Angry")
        val flows = listOf("None", "Light", "Medium", "Heavy")

        binding.dropMood.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, moods)
        )

        binding.dropFlow.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, flows)
        )
    }

    private fun saveDailyLog() {
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(requireContext(), "User belum login", Toast.LENGTH_SHORT).show()
            return
        }

        val mood = binding.dropMood.text.toString().trim()
        val flow = binding.dropFlow.text.toString().trim()
        val note = binding.edtNote.text.toString().trim()

        if (mood.isEmpty() || flow.isEmpty()) {
            Toast.makeText(requireContext(), "Mood dan flow wajib dipilih", Toast.LENGTH_SHORT).show()
            return
        }

        val logData = hashMapOf(
            "userId" to userId,
            "date" to System.currentTimeMillis(),
            "mood" to mood,
            "flowLevel" to flow,
            "note" to note,
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("daily_logs")
            .add(logData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Log berhasil disimpan", Toast.LENGTH_SHORT).show()
                binding.edtNote.setText("")
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal menyimpan log: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}