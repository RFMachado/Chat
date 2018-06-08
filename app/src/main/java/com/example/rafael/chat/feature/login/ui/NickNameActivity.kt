package com.example.rafael.chat.feature.login.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.R
import com.example.rafael.chat.feature.message.ui.MessageActivity
import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.UserPref
import kotlinx.android.synthetic.main.activity_nickname.*

class NickNameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        btnNext.setOnClickListener {
            val nickName = edtNickName.text.toString()
            UserPref(this).set(Consts.USER_NICKNAME, nickName)

            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}