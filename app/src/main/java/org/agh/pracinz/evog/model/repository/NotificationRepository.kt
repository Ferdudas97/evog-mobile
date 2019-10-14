package org.agh.pracinz.evog.model.repository

import org.agh.pracinz.evog.model.remote.NotificationService


class NotificationRepository(private val service: NotificationService) {

    fun reject(id: String) = service.reject(id)

    fun accept(id: String) = service.accept(id)

    fun delete(id: String) = service.deleee(id)

    fun getAll() = service.getAllUserNotifications()

}