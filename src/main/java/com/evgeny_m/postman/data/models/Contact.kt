package com.evgeny_m.postman.data.models

//@Entity(tableName = "contacts_table")
data class Contact(
    //@PrimaryKey(autoGenerate = true)
    //val id: Int,
    //val dataBaseId : String,
    var name: String,
    //val status: String,
    //val photo: String,
    var phone: String
)
