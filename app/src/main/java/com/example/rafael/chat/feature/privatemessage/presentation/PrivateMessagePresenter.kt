package com.example.rafael.chat.feature.privatemessage.presentation

import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.UserPref
import javax.inject.Inject

class PrivateMessagePresenter @Inject constructor(private val userPref: UserPref) {

    fun getPreference() = userPref.getString(Consts.CHANNEL)

}