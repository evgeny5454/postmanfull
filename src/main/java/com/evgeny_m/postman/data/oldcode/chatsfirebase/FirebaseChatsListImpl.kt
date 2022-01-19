package com.evgeny_m.postman.data.oldcode.chatsfirebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.evgeny_m.postman.data.repository.NODE_MESSAGES
import com.google.firebase.database.FirebaseDatabase

private var mutableResult = MutableLiveData<String>()
var result: LiveData<String> = mutableResult


class FirebaseChatsListImpl : FirebaseChatsList {

    var database = FirebaseDatabase.getInstance().reference
    private val refUserMessages = database.child(NODE_MESSAGES).child("1111")

    private var lastMessages = mutableListOf<com.evgeny_m.postman.data.models.DataChatsList>()



    init {
        mutableResult.value = "isPending"
        Log.d("AAAAAA", "init FirebaseChatsListImpl")
        refUserMessages.get()
            .addOnSuccessListener { dataSnapshot ->
                val usersIdList = dataSnapshot.children.map {
                    //UserIdModel(
                    //    userId = it.key.toString()
                    //)
                }
                Log.d("AAAAAA", usersIdList.toString())
                usersIdList.forEach { idModel ->
                    //refUserMessages.child(idModel.userId).get().addOnSuccessListener { snapshot ->
                       // val messages = snapshot.children.map { it.getMessageModel() }
                      //  val counter = messages.size - 3
                       // val lastMessage = messages.last()
                        val item = com.evgeny_m.postman.data.models.DataChatsList(
                            // надо еще по id находить имя пользователя и фото
                            // userId = idModel.userId,
                            name = "Петрович",
                            photo = "https://cdnimg.rg.ru/img/content/177/18/63/1000s_d_850.jpg",
                            //    lastMessage = lastMessage.messageText,
                            //   newMessagesCounter = counter
                        )
                        lastMessages.add(item)
                        if (usersIdList.size == lastMessages.size) {
                            mutableResult.value = "isSuccess"
                        }

                        Log.d("AAAAAA", lastMessages.toString())

                  //  }.addOnFailureListener {
                        //mutableResult.value = "isFail"
                    }
                }
          //  }.addOnFailureListener {
        mutableResult.value = "isFail"
            }
        //Log.d("AAAAAA", "init OK FirebaseChatsListImpl")
    }

    //override fun getChats(): List<DataChatsList> {
        //Log.d("AAAAAA", "FirebaseChatsListImpl return chats")
      //  mutableResult.value = "isOK"
        //return lastMessages
  //  }
//}