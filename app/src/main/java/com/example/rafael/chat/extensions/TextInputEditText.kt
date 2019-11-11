package com.example.rafael.chat.extensions

import android.graphics.PorterDuff
import com.google.android.material.textfield.TextInputEditText
import androidx.core.content.ContextCompat
import com.example.rafael.chat.R

fun TextInputEditText.setColorError(textError: CharSequence?) {
    error = textError

    if (textError == null)
        background.clearColorFilter()
    else
        background.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_ATOP)

}