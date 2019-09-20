package org.agh.pracinz.evog.viewmodel.login

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.Sex
import org.agh.pracinz.evog.model.data.User
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.model.repository.UserRepository
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton


typealias ValidationError = String

@Singleton
class SignInViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    var firstName: String = ""
    var lastName: String = ""
    var birthDate: LocalDate = LocalDate.now()
    var description: String? = null
    var sex: Sex = Sex.MALE
    var phoneNumber: String? = null
    var email: String? = null
    var password: String = ""
    var login: String = " "

    fun create() : Single<Account> {
        val user = User(null, firstName, lastName, birthDate, description, sex, phoneNumber, email)
        val credentials = UserCredentials(login, password)
        return userRepository.createAccount(account = Account(credentials, user))
    }

    fun validatePassoword(password: String): List<ValidationError> {
        if (password.length <= 6) {
            return listOf("password should have minimum 6 chars")
        }
        return emptyList()
    }

    fun validateLogin(login: String): String? {
        if (login.isBlank()) {
            return "login cannot be blank"
        }
        return null
    }


}