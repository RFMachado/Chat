package com.example.rafael.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.ui.MainActivity
import kotlinx.android.synthetic.main.activity_nickname.*

class NickNameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        btnNext.setOnClickListener {
            val nickName = edtNickName.text.toString()
            UserPref(this).set("nickName", nickName)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}