package com.example.rafael.chat.feature.message.ui

import android.os.Bundle
import com.example.rafael.chat.feature.message.Infrastructure.MessagePayload
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.rafael.chat.R
import com.example.rafael.chat.shared.UserPref
import com.example.rafael.chat.feature.message.domain.LeftMessage
import com.example.rafael.chat.feature.message.domain.RightMessage
import com.example.rafael.chat.shared.Consts
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MessageActivity : AppCompatActivity() {

    var items = ArrayList<Any>()

    private lateinit var childEventListener: ChildEventListener
    private lateinit var mMessageReference: DatabaseReference
    var message = MessagePayload()

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        mMessageReference = FirebaseDatabase.getInstance().getReference("message")

        childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) { }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) { }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) { }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val message = dataSnapshot.getValue(MessagePayload::class.java)
                val userId = UserPref(this@MessageActivity).getString(Consts.USER_ID)

                if (message?.userId == userId)
                    items.add(RightMessage(message!!.text, message.nickName))
                else
                    items.add(LeftMessage(message!!.text, message.nickName))

                recyclerView.adapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(items.size - 1)
            }

            override fun onChildRemoved(p0: DataSnapshot) { }

        }

        mMessageReference.addChildEventListener(childEventListener)

        recyclerView.adapter = MessageAdapter(items)

        bindListeners()
    }

    private fun bindListeners() {
        btnSend.setOnClickListener {
            if (!input.text.isEmpty()) {
                message.text = input.text.toString()
                message.userId = UserPref(this).getString(Consts.USER_ID) ?: ""
                message.nickName = UserPref(this).getString(Consts.USER_NICKNAME) ?: ""

                FirebaseDatabase.getInstance()
                        .getReference("message")
                        .push()
                        .setValue(message)
            }

            input.text.clear()
        }
    }

}
