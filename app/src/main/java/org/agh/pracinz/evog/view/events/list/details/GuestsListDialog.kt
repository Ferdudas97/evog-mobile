package org.agh.pracinz.evog.view.events.list.details

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.guest_dialog.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.Participant
import org.agh.pracinz.evog.viewmodel.login.EventDetailsViewModel

class GuestsListDialog(
    private val guests: MutableSet<Participant>,
    private val viewModel: EventDetailsViewModel,
    context: Context
) :
    Dialog(context, R.style.AppTheme), OnGuestItemListener {


    private lateinit var guestAdapter: GuestAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.guest_dialog)

        guestAdapter = GuestAdapter(this@GuestsListDialog, guests.toList(), viewModel.event)
        guestRV.apply {
            adapter = guestAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    override fun onRemoveClicked(eventId: String, guestId: String) {
        viewModel.removeGuest(eventId, guestId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { updateList(guestId) }
            .subscribe()
    }

    override fun loadPhoto(imageView: ImageView, fileId: String) {
        viewModel.loadGuestPhoto(imageView, fileId)
    }

    private fun updateList(guestId: String) {
        guests.removeIf { it.id == guestId }
        guestAdapter.notifyDataSetChanged()
        dismiss()

    }
}