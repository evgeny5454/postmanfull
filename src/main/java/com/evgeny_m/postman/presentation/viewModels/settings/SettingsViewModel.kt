package com.evgeny_m.postman.presentation.viewModels.settings

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evgeny_m.data.models.Contact
import com.evgeny_m.domain.models.DomainDataContact
import com.evgeny_m.domain.usecase.contacts.CheckContactsUseCase
import com.evgeny_m.domain.usecase.contacts.GetContactsFromDatabaseUseCase
import com.evgeny_m.domain.usecase.contacts.GetUserDataByIdUseCase
import com.evgeny_m.domain.usecase.main.EditStatusUseCase
import com.evgeny_m.domain.usecase.settingsfragment.*
import com.evgeny_m.domain.usecase.single_chat.GetMessagesUseCase
import com.evgeny_m.domain.usecase.single_chat.SendMessageUseCase

class SettingsViewModel(
    private val editFullNameUseCase: EditFullNameUseCase,
    private val editPhoneUseCase: EditPhoneUseCase,
    private val editPhotoUseCase: EditPhotoUseCase,
    private val editUserNameUseCase: EditUserNameUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val editBioUseCase: EditBioUseCase,
    private val checkUserNameUseCase: CheckUserNameUseCase,
    private val editStatusUseCase: EditStatusUseCase,
    private val checkContactsUseCase: CheckContactsUseCase,
    private val getContactsFromDatabaseUseCase: GetContactsFromDatabaseUseCase,
    private val getUserDataByIdUseCase: GetUserDataByIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
) : ViewModel() {

    private val mutableCheckUserName = MutableLiveData<Boolean>()
    val checkUserName: LiveData<Boolean> = mutableCheckUserName


    fun editFullName(firstName: String, lastName: String){
        editFullNameUseCase.execute(firstName = firstName, lastName = lastName)
    }

    fun editPhone(string: String) {
        editPhoneUseCase.execute(string)
    }
    fun editBio(string: String){
        editBioUseCase.execute(string)
    }
    fun editPhoto(uri: Uri){
        val string = uri.toString()
        editPhotoUseCase.execute(string)
    }

    fun editUserName(string: String) {
        editUserNameUseCase.execute(string)
    }
    fun logOut(){
        logOutUseCase.logOut()
    }
    fun checkUserName(string: String){
        checkUserNameUseCase.execute(string)
    }

    fun online() {
        editStatusUseCase.online()
    }
    fun offline() {
        editStatusUseCase.offline()
    }
    fun checkContacts(arrayList: ArrayList<Contact>) {
        val arrayListContacts : ArrayList<DomainDataContact> = arrayListOf()
        arrayList.forEach{
            val contact = DomainDataContact(
                name = it.name,
                phone = it.phone
            )
            arrayListContacts.add(contact)
        }
        checkContactsUseCase.execute(arrayListContacts)
    }

    fun loadContacts(): List<DomainDataContact> {
        return getContactsFromDatabaseUseCase.execute()
    }

    fun loadUserData(list: List<DomainDataContact> ) {
        getUserDataByIdUseCase.execute(list)
    }

    fun sendMessage(text: String, userId: String) {
        sendMessageUseCase.execute(text, userId)
    }

    fun loadMessages(userId: String) {
        getMessagesUseCase.execute(userId)
    }
}