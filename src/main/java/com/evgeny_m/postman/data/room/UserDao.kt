package com.evgeny_m.postman.data.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: com.evgeny_m.postman.data.models.User)

    @Update
    suspend fun updateUser(user: com.evgeny_m.postman.data.models.User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<com.evgeny_m.postman.data.models.User>>

    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListContacts(list: List<ContactRoom>)

    @Query("SELECT * FROM contacts_table ORDER BY id ASC")
    fun readAllContacts(): LiveData<List<ContactRoom>>*/
}