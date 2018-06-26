package com.example.rafael.chat.extensions

import android.graphics.PorterDuff
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import com.example.rafael.chat.R

fun TextInputEditText.setColorError(textError: CharSequence?) {
    error = textError

    if (textError == null)
        background.clearColorFilter()
    else
        background.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_ATOP)

}