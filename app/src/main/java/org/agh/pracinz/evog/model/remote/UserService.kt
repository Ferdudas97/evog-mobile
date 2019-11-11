package org.agh.pracinz.evog.model.remote

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.User
import org.agh.pracinz.evog.model.data.UserCredentials
import retrofit2.http.*


interface UserService {
    @POST("accounts/login")
    fun logIn(@Body userCredentials: UserCredentials): Single<Account>

    @Multipart
    @POST("accounts")
    fun createAccount(@Part file: MultipartBody.Part, @Part("account") account: RequestBody): Single<Account>

    @GET("users/{id}")
    fun findById(@Path(value = "id") userId: String): Single<User>
}