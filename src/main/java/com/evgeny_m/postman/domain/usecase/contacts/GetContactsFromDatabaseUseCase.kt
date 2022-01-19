package com.evgeny_m.postman.domain.domain.usecase.contacts

import com.evgeny_m.postman.domain.domain.models.DomainDataContact
import com.evgeny_m.postman.domain.domain.repository.Firebase

class GetContactsFromDatabaseUseCase(private val firebase: Firebase) {

    fun execute(): List<DomainDataContact>{
        //return firebase.getContacts()
        return emptyList()
    }
}