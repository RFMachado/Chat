package com.example.rafael.chat.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View

import com.example.rafael.chat.R



/**
 * Created by Rafael on 22/02/2018.
 */
class MainAdapter: RecyclerView.Adapter(MainAdapter.ViewHolder) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        return
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    }

}