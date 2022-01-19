package com.evgeny_m.postman.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val bio: String,
    val photoUrl: String,
    val phoneNumber: String,
)