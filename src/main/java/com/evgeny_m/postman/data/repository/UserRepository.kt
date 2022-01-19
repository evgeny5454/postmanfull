package com.evgeny_m.postman.data.repository

import androidx.lifecycle.LiveData
import com.evgeny_m.postman.data.room.UserDao

class UserRepository(private val userDao: UserDao) {

    val readAllData : LiveData<List<com.evgeny_m.postman.data.models.User>> =userDao.readAllData()
    //val readAllContacts : LiveData<List<ContactRoom>> =userDao.readAllContacts()

    suspend fun addUser(user: com.evgeny_m.postman.data.models.User) : Boolean {
        userDao.addUser(user)
        return true
    }

    suspend fun updateUser(user: com.evgeny_m.postman.data.models.User) {
        userDao.updateUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    /*suspend fun addListContacts(list: List<ContactRoom>){
        userDao.addListContacts(list)
    }*/
}