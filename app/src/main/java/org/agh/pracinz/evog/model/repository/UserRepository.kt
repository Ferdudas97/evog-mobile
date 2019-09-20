package org.agh.pracinz.evog.model.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.User
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.model.remote.UserService
import javax.inject.Inject
import javax.inject.Singleton


class UserRepository (private val userService: UserService) {


    fun logIn(userCredentials: UserCredentials) : Single<Account> {
        return userService.logIn(userCredentials)
    }


    fun createAccount(account:Account): Single<Account> {

        return userService.createAccount(account)
            .observeOn(Schedulers.io())
    }
}