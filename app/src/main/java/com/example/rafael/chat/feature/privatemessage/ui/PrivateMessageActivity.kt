package com.example.rafael.chat.feature.privatemessage.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.rafael.chat.R
import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.feature.message.ui.MessageAdapter
import kotlinx.android.synthetic.main.activity_private_message.*


class PrivateMessageActivity: AppCompatActivity(), MessageAdapter.Listener {

    private val message by lazy { intent.getParcelableExtra(EXTRA_MESSAGE) as Message? }
    private var items = ArrayList<Any>()

    companion object {
        const val EXTRA_MESSAGE = "message"

        fun launchIntent(context: Context, message: Message): Intent {
            val intent = Intent(context, PrivateMessageActivity::class.java)

            intent.putExtra(EXTRA_MESSAGE, message)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_message)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MessageAdapter(items, this)

        toolbar.title = message?.nickName

        bindListeners()

    }

    private fun bindListeners() {
        arrow.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onClickMessage(leftMessage: Message) {

    }

}