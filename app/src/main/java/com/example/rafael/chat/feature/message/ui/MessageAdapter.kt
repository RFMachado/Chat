package com.example.rafael.chat.feature.message.ui

import com.example.rafael.chat.feature.message.ui.delegate.LeftMessageDelegate
import com.example.rafael.chat.feature.message.ui.delegate.RightMessageDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter


/**
 * Created by Rafael on 22/02/2018.
 */
class MessageAdapter constructor(private val messages: ArrayList<Any>): ListDelegationAdapter<List<Any>>() {

    init {
        delegatesManager
                .addDelegate(LeftMessageDelegate())
                .addDelegate(RightMessageDelegate())
                .fallbackDelegate

        setItems(messages)
    }

}