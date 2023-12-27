package com.mobilebreakero.common_ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun calculateEndDate(startDate: String, duration: String): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()

    calendar.time = sdf.parse(startDate) ?: Date()
    calendar.add(Calendar.DAY_OF_MONTH, duration.toInt())

    return sdf.format(calendar.time)
}


@RequiresApi(Build.VERSION_CODES.O)
fun calculateEndDatef(startDate: String, duration: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())
    val parsedDate = LocalDate.parse(startDate, formatter)
    val endDate = parsedDate.plusDays(duration.toLong())

    return endDate.format(formatter)
}
