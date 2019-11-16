package org.agh.pracinz.evog.model.data

import java.time.LocalDateTime


enum class Category {
    SPORT, PARTY, OTHER
}

enum class Status {
    CANCELED, BEFORE, AFTER, TAKE_PLACE
}


data class Participant(
    val id: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val fileId: String? = null
)

fun Participant.fullName() = "$firstName $lastName"

data class Event(
    val id: String?,
    val imageName: String,
    val name: String,
    val status: Status = Status.BEFORE,
    val details: EventDetails,
    val assigned: Boolean = false,
    val organizers: Participant,
    val guest: Set<Participant>,
    val discussion: Discussion = Discussion()
)

data class Discussion(
    val messages: List<Message> = emptyList()
)

data class Message(
    val creator: Participant,
    val text: String,
    val createdAt: LocalDateTime
)

data class EventDetails(
    val minAllowedAge: Int?,
    val maxAllowedAge: Int?,
    val minNumberOfPeople: Int?,
    val maxNumberOfPeople: Int?,
    val description: String?,
    val startDate: LocalDateTime,
    val endTime: LocalDateTime,
    val localization: Localization,
    val category: Category,
    val isAssigned: Boolean = false
)


data class Localization(
    val latitude: Double,
    val longitude: Double
)

data class EventList(val events: List<EventSnapshot>)

data class EventSnapshot(
    val id: String,
    val name: String,
    val imageName: String,
    val localization: Localization,
    val minNumberOfPeople: Int?,
    val maxNumberOfPeople: Int?,
    val numberOfGuests: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val category: Category
)

data class EventFilter(
    val name: String? = null,
    val minAllowedAge: Int? = null,
    val maxAllowedAge: Int? = null,
    val startTime: LocalDateTime? = null,
    val endTime: LocalDateTime? = null,
    val localizationRadius: Int = 10,
    val maxNumberOfPeople: Int? = null,
    val minNumberOfPeople: Int? = null,
    val category: Category? = null,
    val localization: Localization = Localization(0.0, 0.0),
    val isAssigned: Boolean = false
)