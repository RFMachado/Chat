package com.example.rafael.chat.feature.nickname.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.feature.message.ui.MessageActivity
import com.example.rafael.chat.feature.nickname.presentation.NickNamePresenter
import kotlinx.android.synthetic.main.activity_nickname.*
import javax.inject.Inject

class NickNameActivity: AppCompatActivity() {

    @Inject
    lateinit var presenter: NickNamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)
        MyApplication.coreComponent.inject(this)

        btnNext.setOnClickListener {
            val nickName = edtNickName.text.toString()
            presenter.setPreference(nickName)

            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}