package org.agh.pracinz.evog.viewmodel.login

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.LoggedAcountContextHolder
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.model.repository.UserRepository
import javax.inject.Singleton


@Singleton
class LogInViewModel(private val userRepository: UserRepository) : ViewModel() {


    fun logIn(credentials: UserCredentials): Single<Account> {
        return userRepository.logIn(credentials)
            .doOnSuccess { repoAccount -> LoggedAcountContextHolder.account = repoAccount }
    }
}