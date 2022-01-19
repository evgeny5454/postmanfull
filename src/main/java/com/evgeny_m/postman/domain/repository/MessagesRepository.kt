package com.evgeny_m.postman.domain.domain.repository

import com.evgeny_m.postman.domain.domain.models.DomainMessageModel

interface MessagesRepository {

    fun getMessagesList(): List<DomainMessageModel>

    fun sendMessage(
        message: String,
        currentUserId: String,
        receivingUserId: String,
        type: String
    )
}