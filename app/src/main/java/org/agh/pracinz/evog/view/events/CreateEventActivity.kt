package org.agh.pracinz.evog.view.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_create_event.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Category
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.view.common.MyDatePicker
import org.agh.pracinz.evog.view.common.RxActivity
import org.agh.pracinz.evog.view.onSelectedChange
import org.agh.pracinz.evog.view.onTextChange
import org.agh.pracinz.evog.view.onTextNumberChange
import org.agh.pracinz.evog.view.toPrintable
import org.agh.pracinz.evog.viewmodel.login.CreateEventViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KMutableProperty0

private const val TAG = "CreateEventActivity"

class CreateEventActivity : RxActivity() {

    private lateinit var viewModel: CreateEventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModels.createEventViewModel
        setContentView(R.layout.activity_create_event)
        updateUi()
        setListeners()
    }


    private fun onCreateButtonClick(view: View?) {
        disposables.add(
            viewModel.createEvent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onEventCreated, this::onEventCreationError)
        )
    }

    private fun onEventCreated(event: Event) {
        Toast.makeText(this, "Event ${event.name} is created", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun onEventCreationError(e: Throwable) {
        Log.e(TAG, "Creation event exception ${e.message}")
        Toast.makeText(this, "Event can`t be created", Toast.LENGTH_LONG).show()
    }

    private fun setListeners() {
        createEventButton.setOnClickListener(this::onCreateButtonClick)
        maxPeopleInput.onTextNumberChange { viewModel.state.maxNumberOfPeople = it }
        minPeopleInput.onTextNumberChange { viewModel.state.minNumberOfPeople = it }

        maxAgeInput.onTextNumberChange { viewModel.state.maxAllowedAge = it }
        minAgeInput.onTextNumberChange { viewModel.state.minAllowedAge = it }
        eventNameInput.onTextChange { viewModel.state.name = it }
        startTimeInput.setOnClickListener {
            MyDatePicker(this).createPicker(viewModel.state::startDate) {
                startTimeInput.text = "Start time: ${viewModel.state.startDate.toPrintable()}"
            }
        }
        endTimeInput.setOnClickListener {
            MyDatePicker(this).createPicker(viewModel.state::endTime) {
                endTimeInput.text = "End time: ${viewModel.state.endTime.toPrintable()}"
            }
        }
        categoryRG.onSelectedChange { viewModel.state.category = Category.valueOf(it) }
        eventDescriptionInput.onTextChange { viewModel.state.description = it }
    }

    private fun updateUi() {
        startTimeInput.text = "Start time: ${viewModel.state.startDate.toPrintable()}"
        endTimeInput.text = "End time: ${viewModel.state.endTime.toPrintable()}"
    }



}
