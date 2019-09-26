package org.agh.pracinz.evog.view.events.list

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import org.agh.pracinz.evog.di.manual.ViewModels


class EventFilterDialog(context: Context) : Dialog(context) {


    private val viewModel = ViewModels.eventListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView()
    }


}