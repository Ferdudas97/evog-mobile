package org.agh.pracinz.evog.viewmodel.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.agh.pracinz.evog.model.data.*
import org.agh.pracinz.evog.model.repository.EventRepository
import java.time.LocalDate
import java.time.LocalDateTime


class CreateEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    var state = CreateEventState()

    fun createEvent(): Single<Event> {
        val event = state.toEvent()
        return eventRepository.save(event)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getIcon(name: String) = eventRepository.getIcon(name)

    fun getIcons() = eventRepository.getIcons()

}


private fun CreateEventState.toEvent() = Event(
    id = null,
    name = name,
    imageName = imageName,
    organizers = LoggedAcountContextHolder.account.user.toParticipant(),
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
    Participant(
        id!!,
        firstName,
        lastName,
        age = LocalDate.now().year - this.birthDate.year,
        fileId = this.photosId.firstOrNull()
    )

data class CreateEventState(
    var name: String = "",
    var imageName: String = "top.png",
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