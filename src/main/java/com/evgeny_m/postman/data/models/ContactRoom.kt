package com.evgeny_m.postman.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class ContactRoom(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val dataBaseId : String,
    var name: String,
    val status: String,
    val photo: String,
    val bio: String,
    val phone: String,
    val userName: String
)
