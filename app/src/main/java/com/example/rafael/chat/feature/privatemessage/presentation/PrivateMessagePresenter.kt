package com.example.rafael.chat.feature.privatemessage.presentation

import com.example.rafael.chat.feature.privatemessage.domain.MessagePrivateSource
import com.example.rafael.chat.feature.privatemessage.infrastructure.models.MessagePrivatePayload
import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.ReactivePresenter
import com.example.rafael.chat.shared.UserPref
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class PrivateMessagePresenter @Inject constructor(private val source: MessagePrivateSource, private val userPref: UserPref): ReactivePresenter<PrivateMessageView>() {

    fun sendMessage(input: String, myKey: String, otherKey: String) {
        val message = MessagePrivatePayload()
        val key = if (myKey > otherKey)
            "$myKey$otherKey"
        else
            "$otherKey$myKey"

        message.text = input
        message.userId = userPref.getString(Consts.USER_ID) ?: ""
        message.nickName = userPref.getString(Consts.USER_NICKNAME) ?: ""

        FirebaseDatabase.getInstance()
                .getReference(Consts.PRIVATE).child(key)
                .push()
                .setValue(message)
    }

    fun getPreferenceId() = userPref.getString(Consts.USER_ID) ?: ""

}