package com.evgeny_m.postman.domain.domain.repository

import com.evgeny_m.postman.domain.domain.models.DomainDataContact

interface Firebase {

    fun registerUserByPhoneNumber(phoneNumber: String)
    fun enterSmsCode(code: String)
    fun logOut()
    fun editFullName(firstName: String, lastName: String)
    fun editBio(string: String)
    fun editUserName(string: String)
    fun editPhone(string: String)
    fun editPhoto(string: String)
    fun checkUserName(string: String)
    fun setOnline()
    fun setOffline()
    fun foundUserDataById(userId: String)
    fun checkContacts(array: ArrayList<DomainDataContact>)
    fun sendMessage(message: String, toUserId: String)
    fun getMessagesList(userId: String)
    //fun getContacts() : List<DomainDataContact>
    //fun getUserData(list :List<DomainDataContact>)

}