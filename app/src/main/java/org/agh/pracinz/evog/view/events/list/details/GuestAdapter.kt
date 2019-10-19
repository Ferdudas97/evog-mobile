package org.agh.pracinz.evog.view.events.list.details

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.guest_llist_item.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.Participant
import org.agh.pracinz.evog.view.inflate
import org.agh.pracinz.evog.view.user.UserDetailsActivity


class GuestAdapter(private val guests: List<Participant>) :
    RecyclerView.Adapter<GuestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val inflatedView = parent.inflate(R.layout.guest_llist_item, false)
        return GuestViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = guests.size

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        guests[position].apply {
            holder.bindGuest(this)
        }
    }

}

class GuestViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    fun bindGuest(guest: Participant) {

        guest.apply {
            itemView.guestAge.text = "$age lat"
            itemView.guestName.text = "$firstName $lastName"
        }
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, UserDetailsActivity::class.java)
            intent.putExtra("USER_ID",guest.id)
            itemView.context.startActivity(intent)        }
    }


}