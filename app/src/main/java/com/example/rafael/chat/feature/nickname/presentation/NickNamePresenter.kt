package com.example.rafael.chat.feature.nickname.presentation

import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.UserPref
import javax.inject.Inject

class NickNamePresenter @Inject constructor(private val userPref: UserPref) {

    fun setPreference(nickName: String) {
        userPref.set(Consts.USER_NICKNAME, nickName)
    }
}