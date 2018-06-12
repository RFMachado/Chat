package com.example.rafael.chat.feature.message.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.feature.message.presentation.MessagePresenter
import com.example.rafael.chat.feature.message.presentation.MessageView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MessageActivity : AppCompatActivity(), MessageView {

    var items = ArrayList<Any>()


    @Inject
    lateinit var presenter: MessagePresenter

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyApplication.coreComponent.inject(this)
        presenter.bind(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MessageAdapter(items)

        presenter.fetchMessageData()

        bindListeners()
    }

    override fun showMessage(message: Message) {
        items.add(message)

        recyclerView.adapter.notifyItemInserted(items.size-1)
        recyclerView.layoutManager.scrollToPosition(items.size - 1)
    }

    override fun showError() {

    }

    private fun bindListeners() {
        btnSend.setOnClickListener {
            if (!input.text.isEmpty()) {
                presenter.sendMessage(input.text.toString())
            }

            input.text.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

}
