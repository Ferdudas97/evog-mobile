package org.agh.pracinz.evog.di.manual

import org.agh.pracinz.evog.viewmodel.login.*

object ViewModels {
    val loginViewModel = LogInViewModel(userRepository)
    val singInViewModel = SignInViewModel(userRepository)
    val createEventViewModel = CreateEventViewModel(eventRepository)
    val eventListViewModel = EventListViewModel(eventRepository)
    val eventDetailsViewModel = EventDetailsViewModel(eventRepository)
    val notificationListViewModel = NotificationListViewModel(notificationRepository)
}