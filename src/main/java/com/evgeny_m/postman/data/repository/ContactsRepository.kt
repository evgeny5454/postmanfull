package com.evgeny_m.postman.data.repository

import androidx.lifecycle.LiveData
import com.evgeny_m.postman.data.room.ContactsDao

class ContactsRepository(private val contactsDao: ContactsDao){

    val readAllContacts : LiveData<List<com.evgeny_m.postman.data.models.ContactRoom>> =contactsDao.readAllContacts()

    suspend fun addListContacts(list: List<com.evgeny_m.postman.data.models.ContactRoom>){
        contactsDao.addListContacts(list)
    }
    suspend fun deleteAllData() {
        contactsDao.deleteAllData()
    }
}