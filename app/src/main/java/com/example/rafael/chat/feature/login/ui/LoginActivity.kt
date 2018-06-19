package com.example.rafael.chat.feature.login.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.extensions.toast
import com.example.rafael.chat.feature.login.presentation.LoginPresenter
import com.example.rafael.chat.feature.nickname.ui.NickNameActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity: AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    @Inject
    lateinit var presenter: LoginPresenter

    val RC_SIGN_IN = 9001

    lateinit var mGoogleSignInClient:  GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MyApplication.coreComponent.inject(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

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

                        } else
                            toast(R.string.authentication_failed)
                    }
        }

        btnGoogle.setOnClickListener {
            signIn()
        }


    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)

            } catch (e: ApiException) {
                toast(R.string.authentication_failed_google)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        println("signInWithCredential:success")
                        presenter.setPreference(mAuth.currentUser?.uid)

                        val intent = Intent(this, NickNameActivity::class.java)
                        startActivity(intent)

                    } else {
                        println("signInWithCredential:failure")
                    }
                }
    }

}