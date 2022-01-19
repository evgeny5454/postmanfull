package com.evgeny_m.postman.data.oldcode.localstorage

interface UserStorage {

    fun getUserData(): com.evgeny_m.postman.data.models.DataUserModel
    fun saveUserData(data: com.evgeny_m.postman.data.models.DataUserModel): Boolean
}