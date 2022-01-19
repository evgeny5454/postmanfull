package com.evgeny_m.postman.presentation.userscreens.singlechat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evgeny_m.postman.domain.domain.models.DomainMessageModel
import com.evgeny_m.postman.domain.domain.usecase.single_chat.GetMessagesUseCase
import com.evgeny_m.postman.domain.domain.usecase.single_chat.SendMessageUseCase

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
        //mutableMessages.value = getMessagesUseCase.execute()
    }

}