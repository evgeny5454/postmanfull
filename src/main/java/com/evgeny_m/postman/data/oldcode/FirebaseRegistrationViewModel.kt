package com.evgeny_m.postman.data.oldcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirebaseRegistrationViewModel() : ViewModel() {

    private var mutableRegisterOk = MutableLiveData<Boolean>()
    val registerOk: LiveData<Boolean> = mutableRegisterOk



    init {
        mutableRegisterOk.value = false
    }
    fun registerUser(){

    }

    fun registerOk() {
        mutableRegisterOk.value = true
    }

}