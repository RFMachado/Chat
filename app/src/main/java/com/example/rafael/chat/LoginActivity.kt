package com.example.rafael.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.extensions.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity: AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


        bindListeners()
    }

    private fun bindListeners() {
        btnExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

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
                            currentUser = mAuth.currentUser
                        } else
                            toast(R.string.authentication_failed)
                    }
        }

    }

}