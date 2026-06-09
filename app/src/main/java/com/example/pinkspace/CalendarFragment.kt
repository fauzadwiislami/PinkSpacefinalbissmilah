package com.example.pinkspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pinkspace.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupCalendar()
    }

    private fun setupCalendar() {
        // Set kalender ke tanggal hari ini
        val today = System.currentTimeMillis()
        binding.calendarView.date = today

        // Batasi tanggal, misalnya dari 5 tahun lalu sampai 5 tahun ke depan
        val minCalendar = Calendar.getInstance()
        minCalendar.add(Calendar.YEAR, -5)

        val maxCalendar = Calendar.getInstance()
        maxCalendar.add(Calendar.YEAR, 5)

        binding.calendarView.minDate = minCalendar.timeInMillis
        binding.calendarView.maxDate = maxCalendar.timeInMillis

        // Saat user pilih tanggal
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)

            val selectedDateText = formatDate(selectedCalendar)

            Toast.makeText(
                requireContext(),
                "Tanggal dipilih: $selectedDateText",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun formatDate(calendar: Calendar): String {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return formatter.format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}