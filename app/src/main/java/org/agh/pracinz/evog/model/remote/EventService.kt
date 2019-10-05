package org.agh.pracinz.evog.model.remote

import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.EventSnapshot
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface EventService {

    @POST(value = "events")
    fun create(@Body event: Event): Single<Event>


    @POST(value = "events/filter")
    fun getFilteredEvents(@Body eventFilter: EventFilter): Single<List<EventSnapshot>>

    @GET("events/{id}")
    fun getById(@Path(value = "id") id: String): Single<Event>

    @POST("events/{id}")
    fun assign(@Path(value = "id") id: String): Single<Unit>
}