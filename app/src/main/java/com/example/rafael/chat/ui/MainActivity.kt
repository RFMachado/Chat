package com.example.rafael.chat.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import butterknife.BindView

import butterknife.ButterKnife
import butterknife.OnClick

import com.example.rafael.chat.R
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    @BindView(R.id.edit_text)
    lateinit var input: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

    }

    @OnClick(R.id.button_send)
    fun sendMessage() {
        if(input.toString() != "")
        FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(input.toString())

    }

}
