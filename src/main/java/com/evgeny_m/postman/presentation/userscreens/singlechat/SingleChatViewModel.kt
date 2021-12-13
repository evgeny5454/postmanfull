package com.evgeny_m.postman.presentation.userscreens.singlechat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evgeny_m.domain.models.DomainMessageModel
import com.evgeny_m.domain.usecase.GetMessagesUseCase
import com.evgeny_m.domain.usecase.SendMessageUseCase

class SingleChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private var mutableMessages = MutableLiveData<List<DomainMessageModel>>()
    val messages: LiveData<List<DomainMessageModel>> = mutableMessages

    private var mutableResult = MutableLiveData<Boolean>()
    val result: LiveData<Boolean> = mutableResult

    init {
        loadMessages()
    }

    fun loadMessages() {
        mutableMessages.value = getMessagesUseCase.execute()
    }

    fun sendMessage(text: String) {
        sendMessageUseCase
            .execute(
                message = text,
                "1111",
                "4444",
                "text"
            )
    }
}