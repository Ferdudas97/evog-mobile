package org.agh.pracinz.evog.viewmodel.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.repository.EventRepository


class EventDetailsViewModel(private val eventRepository: EventRepository) : ViewModel() {


    fun getEvent(id: String) : Single<Event> {
        return eventRepository.getById(id)
    }

    fun assign(id: String) : Single<Unit> {
        return eventRepository.assign(id)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getIcon(name: String) = eventRepository.getIcon(name)
}