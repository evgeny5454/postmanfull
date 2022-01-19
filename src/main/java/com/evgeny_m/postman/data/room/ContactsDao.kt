package com.evgeny_m.postman.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListContacts(list: List<com.evgeny_m.postman.data.models.ContactRoom>)

    @Query("DELETE FROM contacts_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM contacts_table ORDER BY id ASC")
    fun readAllContacts(): LiveData<List<com.evgeny_m.postman.data.models.ContactRoom>>
}