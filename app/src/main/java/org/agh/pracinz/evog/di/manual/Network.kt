package org.agh.pracinz.evog.di.manual

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import org.agh.pracinz.evog.model.remote.EventService
import org.agh.pracinz.evog.model.remote.LocalDateAdapter
import org.agh.pracinz.evog.model.remote.LocalDateTimeAdapter
import org.agh.pracinz.evog.model.remote.UserService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime

const val BASE_URL = "http://192.168.0.241:8080/"

private val mapper = GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter()).create()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(mapper))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .build()


val userService = retrofit.create(UserService::class.java)

val eventService = retrofit.create(EventService::class.java)