package com.evgeny_m.postman.domain.domain.usecase

import com.evgeny_m.postman.domain.domain.models.DomainUserModel
import com.evgeny_m.postman.domain.domain.repository.UserRepository

class LoadUserDataUseCase(private val userRepository: UserRepository) {

    fun execute() : DomainUserModel{
        return userRepository.getUserData()
    }
}