package org.agh.pracinz.evog.viewmodel.login

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.agh.pracinz.evog.model.data.*
import org.agh.pracinz.evog.model.repository.EventRepository
import java.time.LocalDate
import java.time.LocalDateTime


class CreateEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    var state = CreateEventState(organizers = setOf(organizer))

    fun createEvent(): Single<Event> {
        val event = state.toEvent()
        return eventRepository.save(event)
    }

}

private val organizer = Participant("1", "radek", "chrzanowski", 23)


private fun CreateEventState.toEvent() = Event(
    id = null,
    name = name,
    organizers = organizers,
    guest = setOf(),
    details = EventDetails(
        minAllowedAge,
        maxAllowedAge,
        minNumberOfPeople,
        maxNumberOfPeople,
        description,
        startDate,
        endTime,
        localization,
        category
    )
)

private fun User.toParticipant() =
    Participant(id!!, firstName, lastName, age = LocalDate.now().year - this.birthDate.year)

data class CreateEventState(
    var name: String = "",
    var organizers: Set<Participant> = setOf(),
    var guest: Set<Participant> = setOf(),
    var minAllowedAge: Int? = null,
    var maxAllowedAge: Int? = null,
    var minNumberOfPeople: Int? = null,
    var maxNumberOfPeople: Int? = null,
    var description: String? = null,
    var startDate: LocalDateTime = LocalDateTime.now(),
    var endTime: LocalDateTime = LocalDateTime.now(),
    var localization: Localization = Localization(0.0, 0.0),
    var category: Category = Category.PARTY
)