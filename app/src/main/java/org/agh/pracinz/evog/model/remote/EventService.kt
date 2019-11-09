package org.agh.pracinz.evog.model.remote

import io.reactivex.Single
import okhttp3.ResponseBody
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.EventSnapshot
import retrofit2.http.*


interface EventService {

    @POST(value = "events")
    fun create(@Body event: Event): Single<Event>

    @DELETE(value = "events/{eventId}/guests/{guestId}")
    fun removeGuest(@Path(value = "eventId") eventId: String, @Path(value = "guestId") userId: String): Single<Unit>

    @POST(value = "events/filter")
    fun getFilteredEvents(@Body eventFilter: EventFilter): Single<List<EventSnapshot>>

    @GET("events/{id}")
    fun getById(@Path(value = "id") id: String): Single<Event>

    @POST("events/{id}/assign")
    fun assign(@Path(value = "id") id: String): Single<Unit>

    @GET("images/{name}")
    fun getEventIcon(@Path(value = "name") name: String): Single<ResponseBody>

    @GET("images")
    fun getEventIconsNames(): Single<List<String>>

}