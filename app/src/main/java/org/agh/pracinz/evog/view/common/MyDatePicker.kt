package org.agh.pracinz.evog.view.common

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.time.LocalDate
import java.time.LocalDateTime


class MyDatePicker(private val context: Context) {


    private val now = LocalDateTime.now()
    private var updateUi: (LocalDate) -> Unit = {}


    private fun onDateSet(
        datePicker: DatePicker?,
        year: Int,
        montOfYeay: Int,
        dayOfMonth: Int
    ) {
        val pickedDate = LocalDate.of(year, montOfYeay, dayOfMonth)
        updateUi(pickedDate)
    }

    fun createPicker(f: (LocalDate) -> Unit) {
        updateUi = f
        DatePickerDialog(
            context,
            this::onDateSet,
            now.year,
            now.monthValue,
            now.dayOfMonth
        )
            .show()
    }
}