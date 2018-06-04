package com.example.rafael.chat.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rafael.chat.R
import kotlinx.android.synthetic.main.layout_adapter.view.*


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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messages = messages[position]

        holder.itemView.textUser?.text = messages

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { }

}