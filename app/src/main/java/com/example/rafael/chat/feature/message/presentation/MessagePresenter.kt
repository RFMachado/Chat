package com.example.rafael.chat.feature.message.presentation

import com.example.rafael.chat.feature.message.infrastructure.models.MessagePayload
import com.example.rafael.chat.feature.message.domain.MessageSource
import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.ReactivePresenter
import com.example.rafael.chat.shared.RxUtils
import com.example.rafael.chat.shared.UserPref
import com.google.firebase.database.*
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject


/**
 * Created by Rafael on 21/02/2018.
 */
class MessagePresenter @Inject constructor(private val source: MessageSource, private val userPref: UserPref): ReactivePresenter<MessageView>() {

    private fun fetchMessageData() {
        val myId = userPref.getString(Consts.USER_ID)

        disposables += source.fetchMessage()
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe { view?.removeAllItems() }
                .subscribe (
                        { message ->
                            message.isMyUser = myId == message.userId

                            view?.showMessage(message)

                        },
                        { view?.showError(it) }
                )
    }

    fun sendMessage(input: String) {
        val message = MessagePayload()
        val channelName = userPref.getString(Consts.CHANNEL) ?: "message"

        message.text = input
        message.userId = userPref.getString(Consts.USER_ID) ?: ""
        message.nickName = userPref.getString(Consts.USER_NICKNAME) ?: ""

        FirebaseDatabase.getInstance()
                .getReference(Consts.CHANNEL).child(channelName)
                .push()
                .setValue(message)
    }

    fun changeChannel(channel: String?) {
        userPref.set(Consts.CHANNEL, channel)

        disposables.clear()
        fetchMessageData()
    }

    fun getPreference() = userPref.getString(Consts.CHANNEL)

}