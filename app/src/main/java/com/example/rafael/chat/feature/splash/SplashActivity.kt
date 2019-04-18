package com.example.rafael.chat.feature.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.R
import com.example.rafael.chat.feature.login.ui.LoginActivity
import com.example.rafael.chat.feature.message.ui.MessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashActivity: AppCompatActivity() {

    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null)
                gotoNextActivity(MessageActivity.launchIntent(this))
            else
                gotoNextActivity(LoginActivity.launchIntent(this))
        }, 700)
    }

    private fun gotoNextActivity(intent: Intent) {
        startActivity(intent)
        finish()
    }

}