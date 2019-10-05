package org.agh.pracinz.evog.viewmodel.login

import androidx.lifecycle.ViewModel
import org.agh.pracinz.evog.model.repository.NotificationRepository


class NotificationListViewModel(private val repository: NotificationRepository) : ViewModel() {

    fun reject(id: String) = repository.reject(id)

    fun accept(id: String) = repository.accept(id)

    fun getAll() = repository.getAll()


}