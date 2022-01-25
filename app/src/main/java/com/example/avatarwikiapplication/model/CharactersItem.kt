package com.example.avatarwikiapplication.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class CharactersItem(
    val _id: String?,
    val affiliation: String?,
    val allies: List<String>,
    val enemies: List<String>,
    val name: String?,
    val photoUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.readString(),
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<CharactersItem> {
        override fun createFromParcel(parcel: Parcel): CharactersItem {
            return CharactersItem(parcel)
        }

        override fun newArray(size: Int): Array<CharactersItem?> {
            return arrayOfNulls(size)
        }
    }
}