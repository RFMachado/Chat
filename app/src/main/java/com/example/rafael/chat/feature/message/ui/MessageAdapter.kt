package com.example.rafael.chat.feature.message.ui

import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.feature.message.ui.delegate.LeftMessageDelegate
import com.example.rafael.chat.feature.message.ui.delegate.RightMessageDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

/**
 * Created by Rafael on 22/02/2018.
 */
class MessageAdapter constructor(messages: ArrayList<Any>, listener: MessageAdapter.Listener) : ListDelegationAdapter<List<Any>>() {

    interface Listener {
        fun onClickMessage(leftMessage: Message)
    }

    init {
        delegatesManager
                .addDelegate(LeftMessageDelegate(listener))
                .addDelegate(RightMessageDelegate())
                .fallbackDelegate

        setItems(messages)
    }
}