package com.example.rafael.chat.ui

import android.os.Bundle
import com.example.rafael.chat.domain.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.rafael.chat.R
import com.example.rafael.chat.UserPref
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var messageList = ArrayList<Message?>()

    private lateinit var childEventListener: ChildEventListener
    private lateinit var mMessageReference: DatabaseReference
    var message = Message()

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
                val message = dataSnapshot.getValue(Message::class.java)
                messageList.add(message)
                recyclerView.adapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messageList.size - 1)
            }

            override fun onChildRemoved(p0: DataSnapshot) { }

        }

        mMessageReference.addChildEventListener(childEventListener)

        recyclerView.adapter = MainAdapter(messageList)

        bindListeners()
    }

    private fun bindListeners() {
        btnSend.setOnClickListener {
            if (!input.text.isEmpty()) {
                message.text = input.text.toString()
                message.userId = UserPref(this).getString("userId") ?: ""

                FirebaseDatabase.getInstance()
                        .getReference("message")
                        .push()
                        .setValue(message)
            }

            input.text.clear()
        }
    }

}
