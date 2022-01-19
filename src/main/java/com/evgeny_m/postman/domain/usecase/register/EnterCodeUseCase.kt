package com.evgeny_m.postman.domain.domain.usecase.register

import com.evgeny_m.postman.domain.domain.repository.Firebase

class EnterCodeUseCase(private val firebaseRegistration: Firebase) {

    fun execute(smsCode: String) {
        firebaseRegistration.enterSmsCode(smsCode)
    }
}