package com.evgeny_m.postman.domain.domain.usecase.settingsfragment

import com.evgeny_m.postman.domain.domain.repository.Firebase

class CheckUserNameUseCase(private val firebase: Firebase) {
    fun execute(string: String){
        return firebase.checkUserName(string = string)
    }
}