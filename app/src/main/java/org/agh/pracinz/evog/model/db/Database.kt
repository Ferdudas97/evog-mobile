package org.agh.pracinz.evog.model.db

import androidx.room.*
import androidx.room.Database
import org.agh.pracinz.evog.model.data.UserCredentials


@Database(entities = arrayOf(UserCredentials::class), version = 1)
abstract class Database : RoomDatabase() {
    abstract fun credentialsDao(): CredentialsDao

}

@Dao
interface CredentialsDao {

    @Query("SELECT * FROM UserCredentials LIMIT 1")
    fun get(): UserCredentials?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(userCredentials: UserCredentials)

    @Query("DELETE FROM UserCredentials WHERE login = :login")
    fun delete(login: String)
}