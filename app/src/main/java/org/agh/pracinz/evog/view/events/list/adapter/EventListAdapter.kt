package org.agh.pracinz.evog.view.events.list.adapter

import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_list_item.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.EventSnapshot
import org.agh.pracinz.evog.view.common.EVENT_ID
import org.agh.pracinz.evog.view.events.list.details.EventDetailsActivity
import org.agh.pracinz.evog.view.inflate
import org.agh.pracinz.evog.view.toPrintable
import org.agh.pracinz.evog.viewmodel.login.EventListViewModel


class EventListAdapter(
    private val events: List<EventSnapshot>,
    private val viewModel: EventListViewModel
) :
    RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflatedView = parent.inflate(R.layout.event_list_item, false)
        return EventViewHolder(inflatedView, viewModel)
    }

    override fun getItemCount(): Int = events.size

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        events[position].apply {
            holder.bindEvent(this)
        }
    }
}


class EventViewHolder(v: View, private val viewModel: EventListViewModel) :
    RecyclerView.ViewHolder(v) {

    private val view = v
    @RequiresApi(Build.VERSION_CODES.P)
    fun bindEvent(event: EventSnapshot) {


        viewModel.getIcon(event.imageName)
            .into(view.eventSnapshotIcon)
        view.apply {
            eventNameTV.text = event.name
            startTimeTV.text = event.startTime.toPrintable()
//            endTimeTV.text = event.endTime.toPrintable()
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