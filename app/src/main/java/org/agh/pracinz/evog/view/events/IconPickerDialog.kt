package org.agh.pracinz.evog.view.events

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.icon_picker_dialog.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.viewmodel.login.CreateEventViewModel


class IconPickerDialog(private val viewModel: CreateEventViewModel, context: Context) :
    Dialog(context, R.style.AppTheme), OnIconClickedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.icon_picker_dialog)
        viewModel.getIcons()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadAdapter).apply {

            }
    }

    private fun loadAdapter(icons: List<String>) {
        iconsGV.adapter = IconAdapter(icons, this, viewModel)
    }

    override fun onIconClicked(name: String) {
        viewModel.state.imageName = name
        dismiss()
    }

}