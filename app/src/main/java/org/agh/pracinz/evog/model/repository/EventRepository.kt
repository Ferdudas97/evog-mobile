package org.agh.pracinz.evog.model.repository

import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.EventSnapshot
import org.agh.pracinz.evog.model.remote.EventService


class EventRepository(private val eventService: EventService) {


    fun save(event: Event): Single<Event> {
        return eventService.create(event)
    }


    fun getFiltered(eventFilter: EventFilter): Single<List<EventSnapshot>> {
        return eventService.getFilteredEvents(eventFilter)
    }

}