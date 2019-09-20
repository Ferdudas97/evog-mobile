package org.agh.pracinz.evog.di.manual

import io.reactivex.schedulers.Schedulers
import org.agh.pracinz.evog.model.remote.UserService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

const val BASE_URL = "http://192.168.0.241:8080/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(JacksonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .build()


val userService = retrofit.create(UserService::class.java)