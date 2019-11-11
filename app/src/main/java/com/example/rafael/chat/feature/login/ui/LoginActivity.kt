package com.example.rafael.chat.feature.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.extensions.setColorError
import com.example.rafael.chat.extensions.textWatcher
import com.example.rafael.chat.extensions.toast
import com.example.rafael.chat.feature.login.presentation.LoginPresenter
import com.example.rafael.chat.feature.message.ui.MessageActivity
import com.example.rafael.chat.feature.nickname.ui.NickNameActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Order
import com.mobsandgeeks.saripaar.annotation.Password
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), Validator.ValidationListener {

    private val mAuth = FirebaseAuth.getInstance()
    private var currentUser: FirebaseUser? = null
    private lateinit var validator: Validator

    @Inject
    lateinit var presenter: LoginPresenter

    @Order(1)
    @NotEmpty
    @Email(messageResId = R.string.error_validation_email)
    lateinit var editEmail: TextInputEditText

    @Order(2)
    @NotEmpty
    @Password(min = 6, messageResId = R.string.error_validation_password)
    lateinit var editPassword: TextInputEditText

    val RC_SIGN_IN = 9001

    lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        fun launchIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth.currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MyApplication.coreComponent.inject(this)

        editEmail = edtEmail
        editPassword = edtPassword

        validator = Validator(this)
        validator.setValidationListener(this)
        validator.validationMode = Validator.Mode.IMMEDIATE

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        bindListeners()
        isLoggedIn()
    }

    private fun bindListeners() {

        edtEmail.textWatcher {
            afterTextChanged {
                validator.validateTill(editEmail)

                if (edtEmail.text.isNullOrEmpty())
                    edtEmail.setColorError(null)
            }
        }

        edtPassword.textWatcher {
            afterTextChanged {
                validator.validateTill(editPassword)

                if (edtPassword.text.isNullOrEmpty())
                    edtPassword.setColorError(null)
            }
        }

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            validator.validate()

            if (!edtEmail.text.isNullOrEmpty() && !edtPassword.text.isNullOrEmpty())
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

            validator.validate()

            if (!edtEmail.text.isNullOrEmpty() && !edtPassword.text.isNullOrEmpty())
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

        btnGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                account?.let { acc ->
                    firebaseAuthWithGoogle(acc)
                }
            } catch (e: ApiException) {
                Timber.e(e)
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
                        finish()
                    } else {
                        println("signInWithCredential:failure")
                    }
                }
    }

    private fun isLoggedIn() {
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            val intent = MessageActivity.launchIntent(this)
            startActivity(intent)
            finish()
        }
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>) {

        for (error in errors) {
            val view = error.view

            if (view == editEmail) {
                editEmail.setColorError(getString(R.string.error_validation_email))
            } else if (view == editPassword) {
                editPassword.setColorError(getString(R.string.error_validation_password))
            }
        }
    }

    override fun onValidationSucceeded() {
        editEmail.setColorError(null)
        editPassword.setColorError(null)
    }
}