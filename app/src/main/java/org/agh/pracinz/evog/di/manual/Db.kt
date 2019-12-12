package org.agh.pracinz.evog.di.manual

import android.content.Context
import androidx.room.Room
import org.agh.pracinz.evog.model.db.Database


object Db {
    private var database: Database? = null

    fun get(context: Context): Database {
        if (database == null) {
            database = Room.databaseBuilder(context, Database::class.java, "EvogDb")
                .build()
        }
        return database!!
    }
}