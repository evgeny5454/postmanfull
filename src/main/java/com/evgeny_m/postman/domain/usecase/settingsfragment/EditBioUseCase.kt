package com.evgeny_m.postman.domain.domain.usecase.settingsfragment

import com.evgeny_m.postman.domain.domain.repository.Firebase

class EditBioUseCase(private val firebase: Firebase) {

    fun execute(string: String){
        firebase.editBio(string = string)
    }
}