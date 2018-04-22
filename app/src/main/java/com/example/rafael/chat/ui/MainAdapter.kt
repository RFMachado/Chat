package com.example.rafael.chat.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.rafael.chat.R
import com.example.rafael.chat.domain.Message
import android.content.Context


/**
 * Created by Rafael on 22/02/2018.
 */
class MainAdapter(private val messages: ArrayList<String>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int { return messages.size }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val messages = messages[position]

        holder?.text1?.text = messages

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.text_user1)
        lateinit var text1: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }

}