package com.example.rafael.chat.feature.message.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    var text: String,
    var nickName: String,
    var userId: String,
    var isMyUser: Boolean = false
) : Parcelable