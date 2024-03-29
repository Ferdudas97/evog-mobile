package org.agh.pracinz.evog.model.repository

import com.squareup.picasso.Picasso
import io.reactivex.Single
import org.agh.pracinz.evog.di.manual.BASE_URL
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.EventSnapshot
import org.agh.pracinz.evog.model.data.Message
import org.agh.pracinz.evog.model.remote.EventService


class EventRepository(private val eventService: EventService) {


    fun removeGuest(eventId: String, userId: String) = eventService.removeGuest(eventId, userId)
    fun save(event: Event): Single<Event> {
        return eventService.create(event)
    }

    fun assign(id: String): Single<Unit> {
        return eventService.assign(id)
    }

    fun getFiltered(eventFilter: EventFilter): Single<List<EventSnapshot>> {
        return eventService.getFilteredEvents(eventFilter)
    }

    fun getById(id: String): Single<Event> {
        return eventService.getById(id)
    }

    fun sendMessage(message: Message, eventId: String) = eventService.sendMessage(message, eventId)

    fun getIcons(): Single<List<String>> = eventService.getEventIconsNames()

//    fun getIcon(name: String) = Picasso.get().load("${path}images/{$name}")


    //    @SuppressLint("NewApi")
//    fun getIcons() : Single<List<Bitmap>> = eventService.getEventIconsNames()
//        .flatMap { name-> name.map { getIcon(it) } }

    fun getIcon(name: String) = Picasso.get().load("${BASE_URL}images/$name")

    fun loadImage(fileId: String) = Picasso.get().load("${BASE_URL}images/nostatic/$fileId")

}