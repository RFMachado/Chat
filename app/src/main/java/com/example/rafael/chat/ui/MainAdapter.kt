package com.example.rafael.chat.ui

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rafael.chat.R
import com.example.rafael.chat.UserPref
import com.example.rafael.chat.delegate.LeftMessageDelegate
import com.example.rafael.chat.delegate.RightMessageDelegate
import com.example.rafael.chat.domain.Message
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import kotlinx.android.synthetic.main.item_delegate_left_message.view.*


/**
 * Created by Rafael on 22/02/2018.
 */
class MainAdapter constructor(private val messages: ArrayList<Any>): ListDelegationAdapter<List<Any>>() {

    init {
        delegatesManager
                .addDelegate(LeftMessageDelegate())
                .addDelegate(RightMessageDelegate())
                .fallbackDelegate

        setItems(messages)
    }

}