package com.evgeny_m.postman.data.oldcode

import com.evgeny_m.postman.data.oldcode.chatsfirebase.FirebaseChatsListImpl
import com.evgeny_m.postman.domain.domain.models.DomainChatModel
import com.evgeny_m.postman.domain.domain.repository.ChatsRepository

class ChatsRepositoryImp(private val chatsStorage: FirebaseChatsListImpl) : ChatsRepository {



    //override fun getChats(): List<DomainChatModel> {
        //val chats = chatsStorage.getChats()
        //return mapChatToDomain(chats)
    //}

    private fun mapChatToDomain(chat: List<com.evgeny_m.postman.data.models.DataChatsList>): List<DomainChatModel> {
        val listToDomain = mutableListOf<DomainChatModel>()
        chat.forEach {
            val item = DomainChatModel(
                id = it.userId,
                name = it.name,
                photo = it.photo,
                lastMessage = it.lastMessage,
                newMessageCounter = it.newMessagesCounter

            )
            listToDomain.add(item)
        }
        return listToDomain
    }
}