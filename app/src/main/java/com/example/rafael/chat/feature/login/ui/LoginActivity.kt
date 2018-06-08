package com.example.rafael.chat.feature.login.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.R
import com.example.rafael.chat.extensions.toast
import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.UserPref
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        bindListeners()
    }

    private fun bindListeners() {

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            println("createUserWithEmail:success")
                            currentUser = mAuth.currentUser
                        } else
                            toast(R.string.authentication_failed)
                    }
        }

        btnSignIn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            println("signInWithEmail:success")
                            UserPref(this).set(Consts.USER_ID, mAuth.currentUser?.uid)

                            val intent = Intent(this, NickNameActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else
                            toast(R.string.authentication_failed)
                    }
        }

    }

}