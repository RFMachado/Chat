package com.example.rafael.chat.ui

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rafael.chat.R
import com.example.rafael.chat.UserPref
import com.example.rafael.chat.domain.Message
import kotlinx.android.synthetic.main.layout_adapter.view.*


/**
 * Created by Rafael on 22/02/2018.
 */
class MainAdapter(private val messages: ArrayList<Message?>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int { return messages.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messages = messages[position]
        val context = holder.itemView.context


        if (messages?.userId.equals(UserPref(context).getString("userId")) ) {
            holder.itemView.textUser?.gravity = Gravity.END
            holder.itemView.textUser?.text = messages?.text
        } else {
            holder.itemView.textUser?.gravity = Gravity.START
            holder.itemView.textUser?.text = messages?.text
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { }

}