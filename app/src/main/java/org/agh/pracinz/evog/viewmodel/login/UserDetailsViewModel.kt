package org.agh.pracinz.evog.viewmodel.login

import org.agh.pracinz.evog.model.repository.UserRepository


class UserDetailsViewModel(private val userRepository: UserRepository) {

    fun getUser(id: String) = userRepository.finById(id)
}