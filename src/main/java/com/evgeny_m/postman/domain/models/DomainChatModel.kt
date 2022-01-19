package com.evgeny_m.postman.domain.domain.models

data class DomainChatModel(
    val id: String,
    val name: String,
    val photo: String,
    val lastMessage: String,
    val newMessageCounter: Int
)
