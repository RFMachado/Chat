package com.example.rafael.chat.delegate

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.rafael.chat.R
import com.example.rafael.chat.domain.LeftMessage
import com.example.rafael.chat.extensions.inflate
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.left_layout_delegate.view.*

class LeftMessageDelegate: AbsListItemAdapterDelegate<LeftMessage, Any, LeftMessageDelegate.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.left_layout_delegate)) {

        fun bind(leftMessage: LeftMessage): Unit = with(leftMessage) {
            itemView.textUser.text = text
            itemView.nickName.text = nickName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int) = item is LeftMessage
    override fun onBindViewHolder(item: LeftMessage, viewHolder: ViewHolder, payloads: MutableList<Any>) = viewHolder.bind(item)

}