package org.agh.pracinz.evog.view.events

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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

private const val TAG = "CreateEventActivity"

class CreateEventActivity : RxActivity() {

    private lateinit var viewModel: CreateEventViewModel

    @RequiresApi(Build.VERSION_CODES.P)
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

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setListeners() {
        createEventImageView.setOnClickListener { openIconPicker() }
        createEventButton.setOnClickListener(this::onCreateButtonClick)
        viewModel.state.apply {
            maxPeopleInput.onTextNumberChange { maxNumberOfPeople = it }
            minPeopleInput.onTextNumberChange { minNumberOfPeople = it }

            maxAgeInput.onTextNumberChange { maxAllowedAge = it }
            minAgeInput.onTextNumberChange { minAllowedAge = it }
            eventNameInput.onTextChange { name = it }
            startTimeInput.setOnClickListener {
                MyDatePicker(this@CreateEventActivity).createPicker(this.startDate) {
                    this.startDate = it
                    startTimeInput.text = "Start time: ${startDate.toPrintable()}"
                }
            }
            endTimeInput.setOnClickListener {
                MyDatePicker(this@CreateEventActivity).createPicker(this.endTime) {
                    this.endTime = it
                    endTimeInput.text = "End time: ${endTime.toPrintable()}"
                }
            }
            categoryRG.onSelectedChange { category = Category.valueOf(it) }
            eventDescriptionInput.onTextChange { description = it }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun openIconPicker() {
        IconPickerDialog(viewModel, this).apply {
            setOnDismissListener {
                setIcon()
            }
            create()
            show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setIcon() = viewModel.getIcon(viewModel.state.imageName).into(createEventImageView)

    @RequiresApi(Build.VERSION_CODES.P)
    private fun updateUi() {
        setIcon()
        startTimeInput.text = "Start time: ${viewModel.state.startDate.toPrintable()}"
        endTimeInput.text = "End time: ${viewModel.state.endTime.toPrintable()}"
    }


}
