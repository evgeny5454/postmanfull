package com.evgeny_m.postman.domain.domain.usecase.single_chat

import com.evgeny_m.postman.domain.domain.repository.Firebase

class GetMessagesUseCase(private val firebase: Firebase) {
    fun execute(userId: String)  {
        firebase.getMessagesList(userId)
    }
}