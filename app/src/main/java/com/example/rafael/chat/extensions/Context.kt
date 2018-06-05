package com.example.rafael.chat.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(resource: Int) =
        Toast.makeText(this, getString(resource), Toast.LENGTH_LONG).show()