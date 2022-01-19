package com.evgeny_m.postman.data.models

data class MessageModel(
    val from: String,
    var messageText: String,
    var messageKey: String
    //var serverTimeStamp: Long = 0,
    //var type: String = "",
) {
    override fun equals(other: Any?): Boolean {
        return (other as com.evgeny_m.postman.data.models.MessageModel).messageKey == messageKey
    }
}
