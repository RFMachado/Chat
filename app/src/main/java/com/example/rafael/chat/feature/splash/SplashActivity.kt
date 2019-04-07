package com.example.rafael.chat.feature.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.R
import com.example.rafael.chat.feature.message.ui.MessageActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            gotoMessageActivity()
        }, 700)
    }

    private fun gotoMessageActivity() {
        val intent = MessageActivity.launchIntent(this)
        startActivity(intent)

        finish()
    }
}