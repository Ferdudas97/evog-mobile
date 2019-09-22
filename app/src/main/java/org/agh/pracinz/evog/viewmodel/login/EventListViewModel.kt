package org.agh.pracinz.evog.viewmodel.login

import androidx.lifecycle.ViewModel
import org.agh.pracinz.evog.di.manual.eventRepository
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.Localization
import org.agh.pracinz.evog.model.repository.EventRepository


class EventListViewModel(private val repository: EventRepository) : ViewModel() {


    val state = EventListState()

    fun getEventsFiltered(eventFilter: EventFilter) = eventRepository.getFiltered(eventFilter)

}


data class EventListState(
    var localization: Localization = Localization(0.0, 0.0)
)




