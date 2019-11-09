package org.agh.pracinz.evog.viewmodel.login

import android.widget.ImageView
import org.agh.pracinz.evog.model.repository.UserRepository


class UserDetailsViewModel(private val userRepository: UserRepository) {

    fun getUser(id: String) = userRepository.finById(id)

    fun loadImage(imageView: ImageView, fileId: String) =
        userRepository.loadImage(fileId).into(imageView)
}