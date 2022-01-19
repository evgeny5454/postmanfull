package com.evgeny_m.postman.data.oldcode

import androidx.lifecycle.LiveData

interface DataRepository {
    
    fun getUserId(): LiveData<String>
    fun getRegisterId(): LiveData<String>
}