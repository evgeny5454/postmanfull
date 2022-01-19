package com.evgeny_m.postman.domain.domain.usecase.settingsfragment

import com.evgeny_m.postman.domain.domain.repository.Firebase

class EditPhoneUseCase(private val firebase: Firebase) {

    fun execute(string: String){
        firebase.editPhone(string = string)
    }
}