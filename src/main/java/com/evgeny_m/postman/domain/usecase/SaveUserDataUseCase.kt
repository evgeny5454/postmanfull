package com.evgeny_m.postman.domain.domain.usecase

import com.evgeny_m.postman.domain.domain.models.DomainUserModel
import com.evgeny_m.postman.domain.domain.repository.UserRepository


class SaveUserDataUseCase(private val userRepository: UserRepository) {

    fun execute(param: DomainUserModel): Boolean {
        return userRepository.saveUserData(saveParam = param)
    }
}