package com.example.rafael.chat.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.rafael.chat.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var messageList = ArrayList<String>()

    lateinit var childEventListener: ChildEventListener
    lateinit var mMessageReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        mMessageReference = FirebaseDatabase.getInstance().getReference("message")

        childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) { }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) { }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) { }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {

                val message = dataSnapshot?.getValue(String::class.java)
                messageList.add(message ?: " ")
                recyclerView.adapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messageList.size - 1)

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

        }

        mMessageReference.addChildEventListener(childEventListener)

        recyclerView.adapter = MainAdapter(messageList)

        bindListeners()
    }

    fun bindListeners() {
        btnSend.setOnClickListener {
            if (!input.text.isEmpty())
                FirebaseDatabase.getInstance()
                        .getReference("message")
                        .push()
                        .setValue(input.text.toString())


            input.text.clear()
        }
    }

}
