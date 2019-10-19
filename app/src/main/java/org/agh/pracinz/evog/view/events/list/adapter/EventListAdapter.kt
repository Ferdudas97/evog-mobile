package org.agh.pracinz.evog.view.events.list.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_list_item.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.EventSnapshot
import org.agh.pracinz.evog.view.common.EVENT_ID
import org.agh.pracinz.evog.view.events.list.details.EventDetailsActivity
import org.agh.pracinz.evog.view.inflate
import org.agh.pracinz.evog.view.toPrintable


class EventListAdapter(private val events: List<EventSnapshot>) :
    RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflatedView = parent.inflate(R.layout.event_list_item, false)
        return EventViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        events[position].apply {
            holder.bindEvent(this)
        }
    }
}


class EventViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val view = v
    fun bindEvent(event: EventSnapshot) {
        view.apply {
            eventNameTV.text = event.name
            startTimeTV.text = event.startTime.toPrintable()
            endTimeTV.text = event.endTime.toPrintable()
            categoryTV.text = event.category.toString()
            peopleLimitTV.text = event.peopleLimit()
        }
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, EventDetailsActivity::class.java)
            intent.putExtra(EVENT_ID, event.id)
            itemView.context.startActivity(intent)
        }
    }

    private fun EventSnapshot.peopleLimit() =
        "$minNumberOfPeople-$maxNumberOfPeople ($numberOfGuests)"
}