package com.evgeny_m.postman.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evgeny_m.postman.data.repository.ContactsRepository
import com.evgeny_m.postman.data.repository.UserRepository
import com.evgeny_m.postman.data.room.ContactsDatabase
import com.evgeny_m.postman.data.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<com.evgeny_m.postman.data.models.User>>
    val readAllContacts : LiveData<List<com.evgeny_m.postman.data.models.ContactRoom>>
    private val repository: UserRepository
    private val contactsRepository: ContactsRepository

    private var mutableUpdateUser = MutableLiveData<Boolean>()
    var updateUser: LiveData<Boolean> = mutableUpdateUser



    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        val contactsDao = ContactsDatabase.getDatabase(application).contactsDao()
        repository = UserRepository(userDao = userDao)
        contactsRepository = ContactsRepository(contactsDao = contactsDao)
        readAllData = repository.readAllData
        readAllContacts = contactsRepository.readAllContacts

    }

    fun addUser(user: com.evgeny_m.postman.data.models.User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: com.evgeny_m.postman.data.models.User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
    fun addListContacts(list: List<com.evgeny_m.postman.data.models.ContactRoom>) {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRepository.addListContacts(list)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRepository.deleteAllData()
        }
    }
}