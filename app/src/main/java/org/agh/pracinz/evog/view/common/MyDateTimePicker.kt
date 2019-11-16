package org.agh.pracinz.evog.view.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class MyDateTimePicker(private val context: Context) {


    private val now = LocalDateTime.now()
    private var updateUi: (LocalDateTime) -> Unit = {}
    private fun onDateSet(localDateTime: LocalDateTime?) =
        { datePicker: DatePicker?, year: Int, montOfYeay: Int, dayOfMonth: Int ->
            onDateSet(datePicker, year, montOfYeay, dayOfMonth, localDateTime)
        }

    private fun onTimeSet(localDateTime: LocalDateTime?) =
        { view: TimePicker?, hourOfDay: Int, minute: Int ->
            onTimeSet(view, hourOfDay, minute, localDateTime)
        }

    private fun onDateSet(
        datePicker: DatePicker?, year: Int, montOfYeay: Int, dayOfMonth: Int,
        localDateTime: LocalDateTime?
    ) {

        val pickedDate = LocalDateTime.of(
            LocalDate.of(year, montOfYeay, dayOfMonth),
            localDateTime?.toLocalTime()
        )
        TimePickerDialog(context, onTimeSet(pickedDate), now.hour, now.minute, true).show()

    }

    private fun onTimeSet(
        view: TimePicker?,
        hourOfDay: Int,
        minute: Int,
        localDateTime: LocalDateTime?
    ) {
        val pickedTime =
            LocalDateTime.of(localDateTime?.toLocalDate(), LocalTime.of(hourOfDay, minute))
        updateUi(pickedTime)
    }

    fun createPicker(localDateTime: LocalDateTime?, f: (LocalDateTime) -> Unit) {
        updateUi = f
        DatePickerDialog(
            context,
            onDateSet(localDateTime),
            now.year,
            now.monthValue,
            now.dayOfMonth
        )
            .show()
    }

    fun setOnTimePicked(f: (LocalDateTime) -> Unit) {
        updateUi = f
    }

}