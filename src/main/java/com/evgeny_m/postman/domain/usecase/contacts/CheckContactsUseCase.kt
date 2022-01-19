package com.evgeny_m.postman.domain.domain.usecase.contacts

import com.evgeny_m.postman.domain.domain.models.DomainDataContact
import com.evgeny_m.postman.domain.domain.repository.Firebase

class CheckContactsUseCase(private val firebase: Firebase) {
    fun execute(arrayContacts: ArrayList<DomainDataContact>){
        firebase.checkContacts(arrayContacts)
    }
}