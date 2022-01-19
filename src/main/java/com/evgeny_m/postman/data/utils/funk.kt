package com.evgeny_m.postman.data.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


val database = FirebaseDatabase.getInstance().reference

const val NODE_MESSAGES = "messages"
const val FROM = "from"
const val MESSAGE_TYPE = "type"
const val TEXT = "messageText"
const val MESSAGE_KEY = "messageKey"
const val SERVER_TIME_STAMP = "serverTimeStamp"


fun sendSingleMessage(
    param: String,
    currentUserId: String,
    receivingUserId: String,
    type: String
) {
    val refDialogUser = "$NODE_MESSAGES/$currentUserId/$receivingUserId"
    val refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserId/$currentUserId"
    val messageKey = database.child(refDialogUser).push().key

    database
        .child(NODE_MESSAGES)
        .child(receivingUserId)
        .child(currentUserId)
        .child("counter").get().addOnSuccessListener {
            val counter = (it.value as Long).toInt()
            val newCounter = counter + 1
            val mapCounter = hashMapOf<String, Any>()
            mapCounter["$refDialogReceivingUser/counter"] = newCounter
            database.updateChildren(mapCounter)
        }

    val mapMessage = hashMapOf<String, Any>()
    mapMessage[FROM] = currentUserId
    mapMessage[MESSAGE_TYPE] = type
    mapMessage[TEXT] = param
    mapMessage[MESSAGE_KEY] = messageKey.toString()
    mapMessage[SERVER_TIME_STAMP] = ServerValue.TIMESTAMP

    val mapNewDialog = hashMapOf<String, Any>()
    mapNewDialog["$refDialogUser/$messageKey"] = mapMessage
    mapNewDialog["$refDialogReceivingUser/$messageKey"] = mapMessage
    database.updateChildren(mapNewDialog)

}

/*fun DataSnapshot.getMessageModel(): MessageModel =
    this.getValue(MessageModel::class.java) ?: MessageModel()*/

fun DataSnapshot.getDataUserModel(): com.evgeny_m.postman.data.models.DataUserModel =
    this.getValue(com.evgeny_m.postman.data.models.DataUserModel::class.java) ?: com.evgeny_m.postman.data.models.DataUserModel()

/*
fun DataSnapshot.getChatModel(): UserIdModel =
    this.getValue(UserIdModel::class.java) ?: UserIdModel()*/
