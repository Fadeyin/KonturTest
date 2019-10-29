package com.example.fadeyin.konturtest.models

import android.os.Parcel
import android.os.Parcelable

import java.util.*



data class User(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: Period
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        TODO("temperament"),
        TODO("educationPeriod")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeFloat(height)
        parcel.writeString(biography)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

    fun getSearchName(): String {
        return this.name
    }
    fun getSearchPhone(): String {
        return this.phone
    }
}

    data class Period(
        val start: Date,
        val end: Date
    )


enum class Temperament {
    melancholic, phlegmatic, sanguine, choleric
}

