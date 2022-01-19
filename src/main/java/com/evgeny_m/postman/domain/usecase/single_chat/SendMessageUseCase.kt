package com.evgeny_m.postman.domain.domain.usecase.single_chat

import com.evgeny_m.postman.domain.domain.repository.Firebase

class SendMessageUseCase(private val firebase: Firebase) {

    fun execute(message: String, userId: String) {
        firebase.sendMessage(message, userId)
    }
}