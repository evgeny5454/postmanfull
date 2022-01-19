package com.evgeny_m.postman.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [com.evgeny_m.postman.data.models.ContactRoom::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    "contacts_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}