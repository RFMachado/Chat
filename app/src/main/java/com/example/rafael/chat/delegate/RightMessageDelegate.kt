package com.example.rafael.chat.delegate

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.rafael.chat.R
import com.example.rafael.chat.domain.RightMessage
import com.example.rafael.chat.extensions.inflate
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.right_layout_delegate.view.*

class RightMessageDelegate: AbsListItemAdapterDelegate<RightMessage, Any, RightMessageDelegate.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.right_layout_delegate)) {

        fun bind(rightMessage: RightMessage): Unit = with(rightMessage) {
            itemView.textUser.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int) = item is RightMessage
    override fun onBindViewHolder(item: RightMessage, viewHolder: ViewHolder, payloads: MutableList<Any>) = viewHolder.bind(item)

}