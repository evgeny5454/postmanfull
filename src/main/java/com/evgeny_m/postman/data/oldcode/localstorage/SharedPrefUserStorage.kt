package com.evgeny_m.postman.data.oldcode.localstorage

import android.content.Context

const val SHARED_PREFS_NAME = "shared_prefs_name"
const val KEY_USER_NAME = "key_user_name"
const val KEY_FIRST_NAME = "key_first_name"
const val KEY_LAST_NAME = "key_last_name"
const val KEY_USER_BIO = "key_user_bio"
const val KEY_USER_PHOTO_URI = "key_user_photo_uri"
const val DEFAULT_STRING = ""

class SharedPrefUserStorage(context: Context) : UserStorage {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun getUserData(): com.evgeny_m.postman.data.models.DataUserModel {
        val userName =
            sharedPreferences.getString(KEY_USER_NAME, DEFAULT_STRING) ?: DEFAULT_STRING
        val userFirstName =
            sharedPreferences.getString(KEY_FIRST_NAME, DEFAULT_STRING) ?: DEFAULT_STRING
        val userLastName =
            sharedPreferences.getString(KEY_LAST_NAME, DEFAULT_STRING) ?: DEFAULT_STRING
        val userBio =
            sharedPreferences.getString(KEY_USER_BIO, DEFAULT_STRING) ?: DEFAULT_STRING
        val userPhotoUri =
            sharedPreferences.getString(KEY_USER_PHOTO_URI, DEFAULT_STRING) ?: DEFAULT_STRING
        return com.evgeny_m.postman.data.models.DataUserModel(
            userName = userName,
            firstName = userFirstName,
            lastName = userLastName,
            bio = userBio,
            userPhotoUri = userPhotoUri
        )
    }

    override fun saveUserData(data: com.evgeny_m.postman.data.models.DataUserModel): Boolean {
        sharedPreferences.edit().putString(KEY_USER_NAME, data.userName).apply()
        sharedPreferences.edit().putString(KEY_FIRST_NAME, data.firstName).apply()
        sharedPreferences.edit().putString(KEY_LAST_NAME, data.lastName).apply()
        sharedPreferences.edit().putString(KEY_USER_BIO, data.bio).apply()
        sharedPreferences.edit().putString(KEY_USER_PHOTO_URI, data.userPhotoUri).apply()
        return true
    }
}