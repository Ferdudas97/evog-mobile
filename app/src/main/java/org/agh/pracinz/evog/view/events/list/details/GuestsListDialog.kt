package org.agh.pracinz.evog.view.events.list.details

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.guest_dialog.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.Participant

class GuestsListDialog(val guests: List<Participant>, context: Context) : Dialog(context, R.style.AppTheme) {



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.guest_dialog)
        guestRV.apply {
            adapter = GuestAdapter(guests)
            layoutManager = LinearLayoutManager(context)
        }

    }

}