package com.evgeny_m.postman.domain.domain.usecase.register

import com.evgeny_m.postman.domain.domain.repository.Firebase

class CheckUserDataUseCase(private val firebaseRegistration: Firebase) {

    fun execute(userId: String){
        firebaseRegistration.foundUserDataById(userId)
    }
}