package org.agh.pracinz.evog.model.data

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.agh.pracinz.evog.model.remote.LocaDateDeserializer
import org.agh.pracinz.evog.model.remote.LocalDateSerializer
import java.time.LocalDate


data class UserCredentials(val login: String, val password: String)

enum class Sex{
    MALE,FEMALE
}

data class Account(val credentials: UserCredentials,
                   val user: User)

data class User(val id: String?,
                val firstName: String,
                val lastName: String,
                @JsonSerialize(using = LocalDateSerializer::class)
                @JsonDeserialize(using =LocaDateDeserializer::class)
                val birthDate: LocalDate,
                val description: String?,
                val sex: Sex,
                val phoneNumber: String?,
                val email: String?)


object LoggedAcountContextHolder {

    lateinit var account: Account
}