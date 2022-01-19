package com.evgeny_m.postman.domain.domain.usecase.settingsfragment

import com.evgeny_m.postman.domain.domain.repository.Firebase

class EditFullNameUseCase(private val firebase: Firebase) {
    fun execute(firstName: String, lastName:String){
        firebase.editFullName(firstName = firstName, lastName = lastName)
    }
}