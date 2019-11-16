package org.agh.pracinz.evog.view.events.list

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.get
import kotlinx.android.synthetic.main.dialog_filter_events.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.Category
import org.agh.pracinz.evog.view.common.MyDateTimePicker
import org.agh.pracinz.evog.view.toIntOrNull
import org.agh.pracinz.evog.view.toPrintable
import org.agh.pracinz.evog.viewmodel.login.EventListViewModel


class EventFilterDialog(private val viewModel: EventListViewModel, context: Context) :
    Dialog(context, R.style.AppTheme) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_filter_events)
        setUpUi()
        setListeners()
        acceptFiltersButton.setOnClickListener(this::onAcceptClick)
    }

    private fun setListeners() {
        viewModel.state.apply {
            endTimeFilterInput.setOnClickListener {
                MyDateTimePicker(context).createPicker(this.endTime) {
                    this.endTime = it
                    endTimeFilterInput.text = "End time: ${endTime?.toPrintable()}"
                }
            }
            startTimeFilterInput.setOnClickListener {
                MyDateTimePicker(context).createPicker(this.startTime) {
                    this.startTime = it
                    startTimeFilterInput.text = "Start time: ${startTime?.toPrintable()}"
                }
            }
            radiusFilter.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    radiusTv.setText("Radius = $progress km",TextView.BufferType.NORMAL)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }

    }

    private fun onAcceptClick(view: View) {
        viewModel.state.apply {
            eventNameFilterInput.text.toString().apply {
                name = if (isNullOrBlank()) null else this
            }
            categoryFilterRG.let {
                val button = it.findViewById(it.checkedRadioButtonId) as RadioButton?
                button?.let {
                    category =
                        Category.valueOf(button.text.toString().toUpperCase())
                }
            }
            localizationRadius = radiusFilter.progress

            maxAllowedAge = maxAgeFilterInput.text.toIntOrNull()
            minAllowedAge = minAgeFilterInput.text.toIntOrNull()
            maxNumberOfPeople = maxPeopleFilterInput.text.toIntOrNull()
            minNumberOfPeople = minPeopleFilterInput.text.toIntOrNull()
        }
        dismiss()
    }

    private fun setUpUi() {
        viewModel.state.apply {

            minPeopleFilterInput.setText(
                minNumberOfPeople?.toString(),
                TextView.BufferType.EDITABLE
            )
            maxPeopleFilterInput.setText(
                maxNumberOfPeople?.toString(),
                TextView.BufferType.EDITABLE
            )
            minAgeFilterInput.setText(minAllowedAge?.toString(), TextView.BufferType.EDITABLE)
            maxAgeFilterInput.setText(maxAllowedAge?.toString(), TextView.BufferType.EDITABLE)
            eventNameFilterInput.setText(name, TextView.BufferType.EDITABLE)
            startTimeFilterInput.setText(
                "Start time: ${startTime?.toPrintable()}",
                TextView.BufferType.EDITABLE
            )
            endTimeFilterInput.setText(
                "End time: ${endTime?.toPrintable()}",
                TextView.BufferType.EDITABLE
            )
            radiusFilter.progress = localizationRadius
            categoryFilterRG.setChecked(category.toString())

        }
    }

    private fun RadioGroup.setChecked(value: String) {
        val count = childCount
        (0 until count).map { this[it] as RadioButton }
            .filter { it.text.toString().toUpperCase() == value.toUpperCase() }
            .forEach { categoryFilterRG.check(it.id) }
    }

}
