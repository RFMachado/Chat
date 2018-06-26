package com.example.rafael.chat.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

inline fun TextView.textWatcher(init: KTextWatcher.() -> Unit): TextWatcher {
    val textWatcher = KTextWatcher().apply(init)
    addTextChangedListener(textWatcher)
    return textWatcher
}

class KTextWatcher : TextWatcher {
    private var _beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var _onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var _afterTextChanged: ((String) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable) {
        _afterTextChanged?.invoke(s.toString())
    }

    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        _beforeTextChanged = listener
    }

    fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        _onTextChanged = listener
    }

    fun afterTextChanged(listener: (String) -> Unit) {
        _afterTextChanged = listener
    }

}