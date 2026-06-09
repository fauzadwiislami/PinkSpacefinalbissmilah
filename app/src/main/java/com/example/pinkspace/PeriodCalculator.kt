package com.example.pinkspace

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object PeriodCalculator {

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    fun calculateNextPeriod(lastPeriodMillis: Long, cycleLength: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lastPeriodMillis
        calendar.add(Calendar.DAY_OF_MONTH, cycleLength)
        return calendar.timeInMillis
    }

    fun calculateOvulation(nextPeriodMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = nextPeriodMillis
        calendar.add(Calendar.DAY_OF_MONTH, -14)
        return calendar.timeInMillis
    }

    fun calculateFertileStart(ovulationMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = ovulationMillis
        calendar.add(Calendar.DAY_OF_MONTH, -5)
        return calendar.timeInMillis
    }

    fun calculateFertileEnd(ovulationMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = ovulationMillis
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        return calendar.timeInMillis
    }

    fun formatDate(timeMillis: Long): String {
        return dateFormat.format(timeMillis)
    }
}