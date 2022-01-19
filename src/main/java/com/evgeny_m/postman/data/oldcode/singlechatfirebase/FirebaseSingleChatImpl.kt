package com.evgeny_m.postman.data.oldcode.singlechatfirebase

import com.evgeny_m.postman.data.utils.AppValueEventListener
import com.evgeny_m.postman.data.utils.NODE_MESSAGES
import com.evgeny_m.postman.data.utils.database
import com.evgeny_m.postman.data.utils.sendSingleMessage
import com.google.firebase.database.DatabaseReference


class FirebaseSingleChatImpl : FirebaseSingleChat {


    private lateinit var refMessages: DatabaseReference
    private lateinit var messagesListener: AppValueEventListener
    private var listMessages = emptyList<com.evgeny_m.postman.data.models.MessageModel>()

    

    init {
        loadMessages()
        //val firebaseChatsList = FirebaseChatsListImpl()
    }

    override fun loadMessages() {
        refMessages = database
            .child(NODE_MESSAGES)
            .child("currentUserID")
            .child("receivingUserID")

       /* messagesListener = AppValueEventListener { dataSnapshot ->
            listMessages = dataSnapshot.children.map {it.getMessageModel()}
        }*/
        refMessages.addValueEventListener(messagesListener)

    }

    override fun sendMessage(
        param: String,
        currentUserId: String,
        receivingUserId: String,
        type: String
    ) {
        sendSingleMessage(param, currentUserId, receivingUserId, type)
        loadMessages()
    }
}