package com.evgeny_m.postman.domain.domain.usecase.settingsfragment

import com.evgeny_m.postman.domain.domain.repository.Firebase

class EditUserNameUseCase(private val firebase: Firebase) {
    fun execute(string: String){
        firebase.editUserName(string = string)
    }
}