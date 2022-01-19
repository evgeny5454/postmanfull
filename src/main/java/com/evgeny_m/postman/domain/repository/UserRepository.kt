package com.evgeny_m.postman.domain.domain.repository

import com.evgeny_m.postman.domain.domain.models.DomainUserModel

interface UserRepository {


    fun getUserData() : DomainUserModel
    fun saveUserData(saveParam: DomainUserModel): Boolean
    //fun getChats() : List<DomainChatModel>
}