package com.evgeny_m.postman.domain.domain.usecase.main

import com.evgeny_m.postman.domain.domain.repository.Firebase

class EditStatusUseCase(private val firebase: Firebase) {

    fun online(){
        firebase.setOnline()
    }

    fun offline(){
        firebase.setOffline()
    }
}