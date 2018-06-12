package com.example.rafael.chat.feature.message.ui.delegate

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.rafael.chat.R
import com.example.rafael.chat.extensions.inflate
import com.example.rafael.chat.feature.message.domain.entities.Message
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_delegate_right_message.view.*

class RightMessageDelegate: AbsListItemAdapterDelegate<Message, Any, RightMessageDelegate.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_delegate_right_message)) {

        fun bind(rightMessage: Message): Unit = with(rightMessage) {
            itemView.textUser.text = text
            itemView.nickName.text = nickName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int) = item is Message && item.isMyUser
    override fun onBindViewHolder(item: Message, viewHolder: ViewHolder, payloads: MutableList<Any>) = viewHolder.bind(item)

}