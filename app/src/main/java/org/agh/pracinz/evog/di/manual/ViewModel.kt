package org.agh.pracinz.evog.di.manual

import org.agh.pracinz.evog.viewmodel.login.CreateEventViewModel
import org.agh.pracinz.evog.viewmodel.login.EventListViewModel
import org.agh.pracinz.evog.viewmodel.login.LogInViewModel
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel

object ViewModels {
    val loginViewModel = LogInViewModel(userRepository)
    val singInViewModel = SignInViewModel(userRepository)
    val createEventViewModel = CreateEventViewModel(eventRepository)
    val eventListViewModel = EventListViewModel(eventRepository)
}