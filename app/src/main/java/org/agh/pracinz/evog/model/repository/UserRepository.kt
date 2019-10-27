package org.agh.pracinz.evog.model.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.model.remote.UserService


class UserRepository (private val userService: UserService) {


    fun logIn(userCredentials: UserCredentials) : Single<Account> {
        return userService.logIn(userCredentials)
    }


    fun createAccount(account:Account): Single<Account> {

        return userService.createAccount(account)
            .observeOn(Schedulers.io())
    }

    fun finById(userId: String) = userService.findById(userId)
}