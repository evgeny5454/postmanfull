package com.evgeny_m.postman.data.oldcode.chatsfirebase.utilits


/*private var database = FirebaseDatabase.getInstance().reference
lateinit var referense: DatabaseReference

var lastMessages = mutableListOf<DataChatsList>()

fun loadUsers() {

    referense = database
        .child(NODE_MESSAGES)
        .child("1111") // надо передать userId

    val chatsListener = AppValueEventListener { dataSnapshot ->
        val usersIdList = dataSnapshot.children.map {
            UserIdModel(
                userId = it.key.toString()
            )
        }
        loadMessagesById(usersIdList)
    }
    referense.addValueEventListener(chatsListener)

}

private fun loadMessagesById(list: List<UserIdModel>) {

    list.forEach { idModel ->
        Log.d("AAAAAA", idModel.userId)
        referense = database
            .child(NODE_MESSAGES)
            .child("1111")// надо передать userId
            .child(idModel.userId)
        val messagesListener = AppValueEventListener { dataSnapshot ->
            val messages = dataSnapshot.children.map {it.getCommonModel()}
            Log.d("AAAAAA", messages.toString())
            getLastMessage(idModel.userId, messages)
        }
        referense.addValueEventListener(messagesListener)

        Log.d("AAAAAA", referense.toString())
    }
}

fun getLastMessage(userId: String, messages: List<MessageModel>) {

    val lastMessage = messages.last()
    val item = DataChatsList( // надо еще по id находить имя пользователя и фото
        userId = userId,
        lastMessage = lastMessage.messageText
    )
    lastMessages.add(item)
    Log.d("AAAAAA", lastMessages.toString())
}*/

