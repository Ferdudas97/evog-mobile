package org.agh.pracinz.evog.viewmodel.login

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import org.agh.pracinz.evog.model.data.LoggedAcountContextHolder
import org.agh.pracinz.evog.model.data.User
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.model.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LogInViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    private val disposables = mutableListOf<Disposable>()

    fun logIn(login: String, password: String) {
        val credentials = UserCredentials(login, password)
        val disposable = userRepository.logIn(credentials)
            .subscribe { repoAccount -> LoggedAcountContextHolder.account = repoAccount }
        disposables.add(disposable)
    }
}