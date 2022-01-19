package com.evgeny_m.postman.data.oldcode.singlechatfirebase

interface FirebaseSingleChat {

    fun sendMessage(param: String, currentUserId: String, receivingUserId: String, type: String)
   fun loadMessages()
}