package org.agh.pracinz.evog.model.repository

import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.agh.pracinz.evog.di.manual.BASE_URL
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.model.remote.UserService


class UserRepository(private val userService: UserService, private val gson: Gson) {


    fun logIn(userCredentials: UserCredentials) : Single<Account> {
        return userService.logIn(userCredentials)
    }


    fun createAccount(account: Account, photos: List<ByteArray>): Single<Account> {
        val reqFile = MultipartBody.create(MediaType.parse("image/*"), photos.first())

        val part = MultipartBody.Part.createFormData("file", "file", reqFile)
        val jsonBody = RequestBody.create(
            MediaType.parse("application/json"), gson.toJson(account)
        )
        return userService.createAccount(part, jsonBody)
            .observeOn(Schedulers.io())
    }

    fun finById(userId: String) = userService.findById(userId)

    fun loadImage(fileId: String) = Picasso.get().load("${BASE_URL}images/nostatic/$fileId")
}

