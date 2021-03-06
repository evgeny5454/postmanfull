package com.evgeny_m.postman.presentation.viewModels.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evgeny_m.postman.data.repository.FirebaseRepository
import com.evgeny_m.postman.domain.domain.usecase.contacts.CheckContactsUseCase
import com.evgeny_m.postman.domain.domain.usecase.contacts.GetContactsFromDatabaseUseCase
import com.evgeny_m.postman.domain.domain.usecase.contacts.GetUserDataByIdUseCase
import com.evgeny_m.postman.domain.domain.usecase.main.EditStatusUseCase
import com.evgeny_m.postman.domain.domain.usecase.settingsfragment.*
import com.evgeny_m.postman.domain.domain.usecase.single_chat.GetMessagesUseCase
import com.evgeny_m.postman.domain.domain.usecase.single_chat.SendMessageUseCase

class SettingsViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val firebase by lazy(LazyThreadSafetyMode.NONE) {
        FirebaseRepository(context)
    }
    private val editUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EditUserNameUseCase(firebase = firebase)
    }
    private val editBioUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EditBioUseCase(firebase = firebase)
    }
    private val editPhotoUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EditPhotoUseCase(firebase = firebase)
    }
    private val editFullNameUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EditFullNameUseCase(firebase = firebase)
    }
    private val editPhoneUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EditPhoneUseCase(firebase = firebase)
    }

    private val logOutUseCase by lazy(LazyThreadSafetyMode.NONE) {
        LogOutUseCase(firebase = firebase)
    }
    private val checkUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) {
        CheckUserNameUseCase(firebase = firebase)
    }

    private val editStatusUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EditStatusUseCase(firebase = firebase)
    }

    private val checkContactsUseCase by lazy(LazyThreadSafetyMode.NONE) {
        CheckContactsUseCase(firebase = firebase)
    }

    private val getContactsFromDatabaseUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetContactsFromDatabaseUseCase(firebase = firebase)
    }

    private val getUserDataByIdUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetUserDataByIdUseCase(firebase = firebase)
    }

    private val sendMessageUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SendMessageUseCase(firebase = firebase)
    }
    private val getMessagesUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetMessagesUseCase(firebase = firebase)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(
            editBioUseCase = editBioUseCase,
            editFullNameUseCase = editFullNameUseCase,
            editPhoneUseCase = editPhoneUseCase,
            editPhotoUseCase = editPhotoUseCase,
            editUserNameUseCase = editUserNameUseCase,
            logOutUseCase = logOutUseCase,
            checkUserNameUseCase = checkUserNameUseCase,
            editStatusUseCase = editStatusUseCase,
            checkContactsUseCase = checkContactsUseCase,
            getContactsFromDatabaseUseCase = getContactsFromDatabaseUseCase,
            getUserDataByIdUseCase = getUserDataByIdUseCase,
            sendMessageUseCase = sendMessageUseCase,
            getMessagesUseCase = getMessagesUseCase
        ) as T
    }
}