package com.evgeny_m.postman.domain.domain.usecase.contacts

import com.evgeny_m.postman.domain.domain.models.DomainDataContact
import com.evgeny_m.postman.domain.domain.repository.Firebase

class GetUserDataByIdUseCase(private val firebase: Firebase) {

    fun execute(list : List<DomainDataContact>){
         //firebase.getUserData(list)
    }
}