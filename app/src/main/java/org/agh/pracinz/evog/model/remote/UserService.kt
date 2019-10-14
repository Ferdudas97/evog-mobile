package org.agh.pracinz.evog.model.remote

import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.UserCredentials
import retrofit2.http.Body
import retrofit2.http.POST


interface UserService {
    @POST("accounts/login")
    fun logIn(@Body userCredentials: UserCredentials): Single<Account>

    @POST("accounts")
    fun createAccount(@Body account: Account): Single<Account>
}