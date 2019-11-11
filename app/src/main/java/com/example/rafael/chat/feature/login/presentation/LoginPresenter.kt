package com.example.rafael.chat.feature.login.presentation

import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.UserPref
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val userPref: UserPref) {

    fun setPreference(uid: String?) {
        userPref.set(Consts.USER_ID, uid)
    }
}