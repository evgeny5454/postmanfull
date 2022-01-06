package com.evgeny_m.postman.presentation.viewModels.register

import androidx.lifecycle.ViewModel
import com.evgeny_m.domain.usecase.register.CheckUserDataUseCase
import com.evgeny_m.domain.usecase.register.EnterCodeUseCase
import com.evgeny_m.domain.usecase.register.EnterPhoneNumberUseCase

class RegisterViewModel(
    private val checkUserDataUseCase: CheckUserDataUseCase,
    private val enterCodeUseCase: EnterCodeUseCase,
    private val enterPhoneNumberUseCase: EnterPhoneNumberUseCase
) : ViewModel() {

    fun checkData(string: String) {
        checkUserDataUseCase.execute(string)
    }

    fun enterPhone(string: String) {
        enterPhoneNumberUseCase.execute(string)
    }
    fun enterCode(string: String) {
        enterCodeUseCase.execute(string)
    }
}