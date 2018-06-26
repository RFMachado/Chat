package com.example.rafael.chat.feature.nickname.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.extensions.setColorError
import com.example.rafael.chat.extensions.textWatcher
import com.example.rafael.chat.feature.message.ui.MessageActivity
import com.example.rafael.chat.feature.nickname.presentation.NickNamePresenter
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Order
import com.mobsandgeeks.saripaar.annotation.Password
import kotlinx.android.synthetic.main.activity_nickname.*
import javax.inject.Inject

class NickNameActivity: AppCompatActivity(), Validator.ValidationListener {

    @Inject
    lateinit var presenter: NickNamePresenter

    private lateinit var validator: Validator

    @Order(1)
    @NotEmpty
    @Password(min = 2, scheme = Password.Scheme.ALPHA, messageResId = R.string.error_validation_nickname)
    lateinit var edtNick: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)
        MyApplication.coreComponent.inject(this)

        edtNick = edtNickName

        validator = Validator(this)
        validator.setValidationListener(this)
        validator.validationMode = Validator.Mode.IMMEDIATE


        btnNext.setOnClickListener {
            val nickName = edtNickName.text.toString()
            presenter.setPreference(nickName)

            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
            finish()
        }

        edtNickName.textWatcher {
            afterTextChanged {
                validator.validateTill(edtNick)

                if (edtNickName.text.isEmpty()) {
                    edtNickName.setColorError(null)
                }
            }
        }
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>) {

        for (error in errors) {
            val view = error.view

            if (view == edtNick) {
                btnNext.isEnabled = false
                edtNick.setColorError(getString(R.string.error_validation_nickname))
            }
        }

    }

    override fun onValidationSucceeded() {
        btnNext.isEnabled = true
        edtNick.setColorError(null)
    }

}