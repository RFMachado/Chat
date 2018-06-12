package com.example.rafael.chat.feature.login.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.extensions.toast
import com.example.rafael.chat.feature.login.presentation.LoginPresenter
import com.example.rafael.chat.feature.nickname.ui.NickNameActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity: AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MyApplication.coreComponent.inject(this)

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
                            presenter.setPreference(mAuth.currentUser?.uid)

                            val intent = Intent(this, NickNameActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else
                            toast(R.string.authentication_failed)
                    }
        }

    }

}