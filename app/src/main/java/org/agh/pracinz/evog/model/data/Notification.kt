package org.agh.pracinz.evog.model.data

import java.time.LocalDateTime

enum class State {
    REJECTED, ACCEPTED, NOT_ACTION
}

data class Notification(
    val id: String,
    val guest: Participant,
    val eventId: String,
    val content: String,
    val state: State,
    val creationTime: LocalDateTime
)