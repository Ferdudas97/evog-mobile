package org.agh.pracinz.evog.viewmodel.login

import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.LoggedAcountContextHolder
import org.agh.pracinz.evog.model.data.Message
import org.agh.pracinz.evog.model.data.toParticipant
import org.agh.pracinz.evog.model.repository.EventRepository
import java.time.LocalDateTime


class EventDetailsViewModel(private val eventRepository: EventRepository) : ViewModel() {


    lateinit var event: Event

    fun removeGuest(eventId: String, userId: String) = eventRepository.removeGuest(eventId, userId)

    fun getEvent(id: String): Single<Event> {
        return eventRepository.getById(id).doOnSuccess { event = it }
    }

    fun assign(id: String): Single<Unit> {
        return eventRepository.assign(id)
    }

    fun sendMessage(content: String): Single<Unit> {
        val msg = Message(
            text = content,
            createdAt = LocalDateTime.now(),
            creator = LoggedAcountContextHolder.account.user.toParticipant()
        )
        return eventRepository.sendMessage(msg, eventId = event.id!!)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getIcon(name: String) = eventRepository.getIcon(name)

    fun loadGuestPhoto(imageView: ImageView, fileId: String) =
        eventRepository.loadImage(fileId).into(imageView)


}