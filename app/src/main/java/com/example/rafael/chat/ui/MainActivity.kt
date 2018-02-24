package com.example.rafael.chat.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import butterknife.ButterKnife
import butterknife.OnClick

import com.example.rafael.chat.R
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

    }

    @OnClick(R.id.button_send)
    fun sendMessage(input: String) {
        FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(input)

    }

}
