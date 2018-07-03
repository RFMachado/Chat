package com.example.rafael.chat.feature.privatemessage.presentation

import com.example.rafael.chat.feature.privatemessage.domain.MessagePrivateSource
import com.example.rafael.chat.feature.privatemessage.infrastructure.models.MessagePrivatePayload
import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.ReactivePresenter
import com.example.rafael.chat.shared.RxUtils
import com.example.rafael.chat.shared.UserPref
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class PrivateMessagePresenter @Inject constructor(private val source: MessagePrivateSource, private val userPref: UserPref): ReactivePresenter<PrivateMessageView>() {

    fun sendMessage(input: String, otherKey: String) {
        val message = MessagePrivatePayload()
        val myKey = userPref.getString(Consts.USER_ID) ?: ""
        val key = if (myKey > otherKey)
            "$myKey$otherKey"
        else
            "$otherKey$myKey"

        message.text = input
        message.userId = myKey
        message.nickName = userPref.getString(Consts.USER_NICKNAME) ?: ""

        FirebaseDatabase.getInstance()
                .getReference(Consts.PRIVATE).child(key)
                .push()
                .setValue(message)
    }

    fun fetchMessage(otherKey: String) {
        val myKey = userPref.getString(Consts.USER_ID) ?: ""
        val key = if (myKey > otherKey)
            "$myKey$otherKey"
        else
            "$otherKey$myKey"

        disposables += source.fetchMessage(key)
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe { view?.removeAllItems() }
                .subscribe (
                        { message ->
                            message.isMyUser = myKey == message.userId

                            view?.showMessage(message)

                        },
                        { view?.showError() }
                )
    }

}