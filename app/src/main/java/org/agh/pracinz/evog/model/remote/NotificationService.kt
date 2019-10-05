package org.agh.pracinz.evog.model.remote

import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Notification
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


const val path = "/notifications/{id}/"

interface NotificationService {

    @POST("$path/reject")
    fun reject(@Path(value = "id") id: String): Single<Unit>

    @POST("$path/accept")
    fun accept(@Path(value = "id") id: String): Single<Unit>

    @GET
    fun getAllUserNotifications(): Single<List<Notification>>
}