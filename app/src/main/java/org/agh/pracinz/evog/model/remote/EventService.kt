package org.agh.pracinz.evog.model.remote

import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.EventSnapshot
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface EventService {

    @POST(value = "events")
    fun create(@Body event: Event): Single<Event>


    @GET(value = "events")
    fun getFilteredEvents(@Body eventFilter: EventFilter): Single<List<EventSnapshot>>
}