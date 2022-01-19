package com.evgeny_m.postman.domain.domain.usecase.settingsfragment

import com.evgeny_m.postman.domain.domain.repository.Firebase

class LogOutUseCase(private val firebase: Firebase) {
    fun logOut(){
        firebase.logOut()
    }
}