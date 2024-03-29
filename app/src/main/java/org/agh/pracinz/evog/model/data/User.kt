package org.agh.pracinz.evog.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity
data class UserCredentials(@PrimaryKey val login: String, val password: String)

enum class Sex{
    MALE,FEMALE
}

data class Account(val credentials: UserCredentials,
                   val user: User)

data class User(val id: String?,
                val firstName: String,
                val lastName: String,
//                @JsonSerialize(using = LocalDateSerializer::class)
//                @JsonDeserialize(using =LocaDateDeserializer::class)
//                @JsonFormat(pattern = "YYYY-MM-DD")
                val birthDate: LocalDate,
                val description: String?,
                val sex: Sex,
                val phoneNumber: String?,
                val email: String?,
                val photosId: List<String> = emptyList()
)

fun User.toParticipant() =
    Participant(
        id!!,
        firstName,
        lastName,
        age = LocalDate.now().year - this.birthDate.year,
        fileId = this.photosId.firstOrNull()
    )


object LoggedAcountContextHolder {

    var account: Account? = null
}