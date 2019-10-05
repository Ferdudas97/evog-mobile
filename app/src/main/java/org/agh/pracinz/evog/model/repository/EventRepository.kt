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

    fun assign(id: String) : Single<Unit> {
        return eventService.assign(id)
    }

    fun getFiltered(eventFilter: EventFilter): Single<List<EventSnapshot>> {
        return eventService.getFilteredEvents(eventFilter)
    }

    fun getById(id:String): Single<Event> {
        return eventService.getById(id)
    }

}