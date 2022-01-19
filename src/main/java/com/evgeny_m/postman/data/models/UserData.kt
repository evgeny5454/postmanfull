package com.evgeny_m.postman.data.models

import android.os.Parcel
import android.os.Parcelable

data class UserData(
    val id: String?,
    val name: String?,
    val status: String?,
    val photo: String?,
    val bio: String?,
    val phone: String?,
    val userName: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(photo)
        parcel.writeString(bio)
        parcel.writeString(phone)
        parcel.writeString(userName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.evgeny_m.postman.data.models.UserData> {
        override fun createFromParcel(parcel: Parcel): com.evgeny_m.postman.data.models.UserData {
            return com.evgeny_m.postman.data.models.UserData(parcel)
        }

        override fun newArray(size: Int): Array<com.evgeny_m.postman.data.models.UserData?> {
            return arrayOfNulls(size)
        }
    }
}
