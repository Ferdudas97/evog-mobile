package org.agh.pracinz.evog.di.manual

import org.agh.pracinz.evog.viewmodel.login.CreateEventViewModel
import org.agh.pracinz.evog.viewmodel.login.LogInViewModel
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel

object ViewModels {
    val loginViewModel = LogInViewModel(userRepository)
    val singInViewModel = SignInViewModel(userRepository)
    var createEventViewModel = CreateEventViewModel(eventRepository)
}