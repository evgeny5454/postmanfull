package com.evgeny_m.postman.presentation.viewModels.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evgeny_m.data.repository.FirebaseRepository
import com.evgeny_m.domain.usecase.main.EditStatusUseCase
import com.evgeny_m.domain.usecase.register.CheckUserDataUseCase
import com.evgeny_m.domain.usecase.register.EnterCodeUseCase
import com.evgeny_m.domain.usecase.register.EnterPhoneNumberUseCase
import com.evgeny_m.domain.usecase.settingsfragment.*
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel

class RegisterViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val firebase by lazy(LazyThreadSafetyMode.NONE) {
        FirebaseRepository(context)
    }
    private val checkUserDataUseCase by lazy(LazyThreadSafetyMode.NONE) {
        CheckUserDataUseCase(firebase)
    }
    private val enterCodeUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EnterCodeUseCase(firebase)
    }
    private val enterPhoneNumberUseCase by lazy(LazyThreadSafetyMode.NONE) {
        EnterPhoneNumberUseCase(firebase)
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(
            checkUserDataUseCase = checkUserDataUseCase,
            enterCodeUseCase = enterCodeUseCase,
            enterPhoneNumberUseCase = enterPhoneNumberUseCase
        ) as T
    }
}