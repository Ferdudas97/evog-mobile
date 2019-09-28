package org.agh.pracinz.evog.view.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KMutableProperty0


class MyDatePicker(private val context: Context) {


    private val now = LocalDateTime.now()
    private var updateUi : () -> Unit = {}
    private fun onDateSet(property0: KMutableProperty0<LocalDateTime>) =
        { datePicker: DatePicker?, year: Int, montOfYeay: Int, dayOfMonth: Int ->
            onDateSet(datePicker, year, montOfYeay, dayOfMonth, property0)
        }

    private fun onTimeSet(property0: KMutableProperty0<LocalDateTime>) =
        { view: TimePicker?, hourOfDay: Int, minute: Int ->
            onTimeSet(view, hourOfDay, minute, property0)
        }

    private fun onDateSet(
        datePicker: DatePicker?, year: Int, montOfYeay: Int, dayOfMonth: Int,
        property0: KMutableProperty0<LocalDateTime>
    ) {
        property0.set(
            LocalDateTime.of(
                LocalDate.of(year, montOfYeay, dayOfMonth),
                property0.get().toLocalTime()
            )
        )
        TimePickerDialog(context, onTimeSet(property0), now.hour, now.minute, true).show()

    }

    private fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int, property0: KMutableProperty0<LocalDateTime>) {
        property0.set(LocalDateTime.of(property0.get().toLocalDate(), LocalTime.of(hourOfDay, minute)))
        updateUi()
    }

    fun createPicker(property0: KMutableProperty0<LocalDateTime>,f: () -> Unit) {
        updateUi = f
        DatePickerDialog(
            context,
            onDateSet(property0),
            now.year,
            now.monthValue,
            now.dayOfMonth
        )
            .show()
    }

    fun setOnTimePicked(f: () -> Unit) {
        updateUi = f
    }

}