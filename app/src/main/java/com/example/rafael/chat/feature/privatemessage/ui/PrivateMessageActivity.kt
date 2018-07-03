package com.example.rafael.chat.feature.privatemessage.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.feature.message.ui.MessageAdapter
import com.example.rafael.chat.feature.privatemessage.presentation.PrivateMessagePresenter
import com.example.rafael.chat.feature.privatemessage.presentation.PrivateMessageView
import kotlinx.android.synthetic.main.activity_private_message.*
import javax.inject.Inject


class PrivateMessageActivity: AppCompatActivity(), MessageAdapter.Listener, PrivateMessageView {

    private val message by lazy { intent.getParcelableExtra(EXTRA_MESSAGE) as Message }
    private var items = ArrayList<Any>()

    @Inject
    lateinit var presenter: PrivateMessagePresenter

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
        MyApplication.coreComponent.inject(this)
        presenter.bind(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MessageAdapter(items, this)

        toolbar.title = message.nickName

        bindListeners()

        presenter.fetchMessage(message.userId)

    }

    private fun bindListeners() {
        arrow.setOnClickListener {
            onBackPressed()
        }

        btnSend.setOnClickListener {
            if (!input.text.isEmpty())
                presenter.sendMessage(input.text.toString(), message.userId)

            input.text.clear()
        }
    }

    override fun showMessage(message: Message) {
        items.add(message)

        recyclerView.adapter.notifyItemInserted(items.size-1)
        recyclerView.layoutManager.scrollToPosition(items.size - 1)
    }

    override fun showError() {

    }

    override fun removeAllItems() {
        items.mapIndexed { index, item -> Pair(index, item) }
                .asReversed()
                .forEach { (index) ->
                    items.removeAt(index)
                    recyclerView.adapter.notifyItemRemoved(index)
                }
    }

    override fun onClickMessage(leftMessage: Message) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

}