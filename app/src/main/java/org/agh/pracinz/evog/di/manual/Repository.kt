package org.agh.pracinz.evog.di.manual

import org.agh.pracinz.evog.model.repository.EventRepository
import org.agh.pracinz.evog.model.repository.NotificationRepository
import org.agh.pracinz.evog.model.repository.UserRepository


val userRepository = UserRepository(userService)

val eventRepository = EventRepository(eventService)

val notificationRepository = NotificationRepository(notificationService)