package com.evgeny_m.postman.data.oldcode

import com.evgeny_m.postman.data.oldcode.localstorage.UserStorage
import com.evgeny_m.postman.domain.domain.models.DomainUserModel
import com.evgeny_m.postman.domain.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userStorage: UserStorage
    //private val chatsStorage: FirebaseChatsListImpl

) : UserRepository {
    override fun saveUserData(saveParam: DomainUserModel): Boolean {
        val data = mapToStorage(saveParam)
        return userStorage.saveUserData(data)
    }

    /*override fun getChats(): List<DomainChatModel> {
        val chats = chatsStorage.getChats()
        return mapChatToDomain(chats)
    }*/

    override fun getUserData(): DomainUserModel {
        val userData = userStorage.getUserData()
        return mapToDomain(userData)
    }

    private fun mapToStorage(saveParam: DomainUserModel): com.evgeny_m.postman.data.models.DataUserModel {
        return com.evgeny_m.postman.data.models.DataUserModel(
            userName = saveParam.userName,
            firstName = saveParam.firstName,
            lastName = saveParam.lastName,
            bio = saveParam.bio,
            userPhotoUri = saveParam.userPhotoUri
        )
    }

    private fun mapToDomain(dataUserModel: com.evgeny_m.postman.data.models.DataUserModel): DomainUserModel {
        return DomainUserModel(
            userName = dataUserModel.userName,
            firstName = dataUserModel.firstName,
            lastName = dataUserModel.lastName,
            bio = dataUserModel.bio,
            userPhotoUri = dataUserModel.userPhotoUri
        )
    }

   /* private fun mapChatToDomain(chat: List<DataChatsList>): List<DomainChatModel> {
        val listToDomain = mutableListOf<DomainChatModel>()
        chat.forEach {
            val item = DomainChatModel(
                id = it.userId,
                name = it.name,
                photo = it.photo,
                lastMessage = it.lastMessage
            )
            listToDomain.add(item)
        }
        return listToDomain
    }*/
}