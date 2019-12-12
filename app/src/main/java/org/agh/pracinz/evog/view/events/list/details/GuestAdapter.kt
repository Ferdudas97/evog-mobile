package org.agh.pracinz.evog.view.events.list.details

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.guest_llist_item.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.LoggedAcountContextHolder
import org.agh.pracinz.evog.model.data.Participant
import org.agh.pracinz.evog.view.inflate
import org.agh.pracinz.evog.view.user.UserDetailsActivity


class GuestAdapter(
    private val listner: OnGuestItemListener,
    private val guests: List<Participant>, private val event: Event
) :
    RecyclerView.Adapter<GuestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val inflatedView = parent.inflate(R.layout.guest_llist_item, false)
        return GuestViewHolder(event, listner, inflatedView)
    }

    override fun getItemCount(): Int = guests.size

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        guests[position].apply {
            holder.bindGuest(this)
        }
    }

}

interface OnGuestItemListener {
    fun onRemoveClicked(eventId: String, guestId: String)
    fun loadPhoto(imageView: ImageView, fileId: String)
}

class GuestViewHolder(
    private val event: Event,
    private val listner: OnGuestItemListener,
    v: View
) : RecyclerView.ViewHolder(v) {

    fun bindGuest(guest: Participant) {

        guest.apply {
            itemView.guestAge.text = "$age lat"
            itemView.guestName.text = "$firstName $lastName"
            if (LoggedAcountContextHolder.account!!.user.id == event.organizers.id) {
                itemView.deleteGuestButton.setOnClickListener {
                    listner.onRemoveClicked(
                        event.id!!,
                        guest.id
                    )
                }
            } else {
                itemView.actionButtonsGuestItem.visibility = View.GONE
            }
        }
        guest.fileId?.let {
            listner.loadPhoto(itemView.guestPhoto, it)
        }
        itemView.setOnClickListener {
            UserDetailsActivity.open(itemView.context, guest.id)
        }
    }


}