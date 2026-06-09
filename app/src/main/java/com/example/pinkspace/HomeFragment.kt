package com.example.pinkspace

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pinkspace.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private var selectedLastPeriodMillis: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadLatestPrediction()

        binding.edtLastPeriod.setOnClickListener {
            showDatePicker()
        }

        binding.btnPredict.setOnClickListener {
            predictAndSave()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val dialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, day, 0, 0, 0)
                selectedCalendar.set(Calendar.MILLISECOND, 0)

                selectedLastPeriodMillis = selectedCalendar.timeInMillis
                binding.edtLastPeriod.setText(
                    PeriodCalculator.formatDate(selectedLastPeriodMillis)
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        dialog.show()
    }

    private fun predictAndSave() {
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(requireContext(), "User belum login", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedLastPeriodMillis == 0L) {
            Toast.makeText(requireContext(), "Pilih tanggal haid terakhir dulu", Toast.LENGTH_SHORT).show()
            return
        }

        val cycleLength = binding.edtCycleLength.text.toString().trim().toIntOrNull()

        if (cycleLength == null || cycleLength < 20 || cycleLength > 45) {
            Toast.makeText(requireContext(), "Panjang siklus harus 20–45 hari", Toast.LENGTH_SHORT).show()
            return
        }

        val nextPeriod = PeriodCalculator.calculateNextPeriod(
            selectedLastPeriodMillis,
            cycleLength
        )

        val ovulation = PeriodCalculator.calculateOvulation(nextPeriod)
        val fertileStart = PeriodCalculator.calculateFertileStart(ovulation)
        val fertileEnd = PeriodCalculator.calculateFertileEnd(ovulation)

        binding.txtNextPeriod.text = PeriodCalculator.formatDate(nextPeriod)
        binding.txtFertileWindow.text =
            "Fertile window: ${PeriodCalculator.formatDate(fertileStart)} - ${PeriodCalculator.formatDate(fertileEnd)}"

        val cycleData = hashMapOf(
            "userId" to userId,
            "lastPeriodDate" to selectedLastPeriodMillis,
            "cycleLength" to cycleLength,
            "predictedNextPeriod" to nextPeriod,
            "ovulationDate" to ovulation,
            "fertileStart" to fertileStart,
            "fertileEnd" to fertileEnd,
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("cycles")
            .add(cycleData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Prediction saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal menyimpan data: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadLatestPrediction() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("cycles")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val doc = result.documents[0]
                    val nextPeriod = doc.getLong("predictedNextPeriod")
                    val fertileStart = doc.getLong("fertileStart")
                    val fertileEnd = doc.getLong("fertileEnd")

                    if (nextPeriod != null) {
                        binding.txtNextPeriod.text = PeriodCalculator.formatDate(nextPeriod)
                    }

                    if (fertileStart != null && fertileEnd != null) {
                        binding.txtFertileWindow.text =
                            "Fertile window: ${PeriodCalculator.formatDate(fertileStart)} - ${PeriodCalculator.formatDate(fertileEnd)}"
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}