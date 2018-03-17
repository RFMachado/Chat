package com.example.rafael.chat.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import butterknife.BindView

import butterknife.ButterKnife
import butterknife.OnClick

import com.example.rafael.chat.R
import com.example.rafael.chat.domain.Message
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference


class MainActivity : AppCompatActivity() {

    @BindView(R.id.edit_text)
    lateinit var input: EditText

    private val messageList: ArrayList<Message>? = null

    lateinit var childEventListener: ChildEventListener
    lateinit var mMessageReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        mMessageReference = FirebaseDatabase.getInstance().getReference("message")

        childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                println("<<< Changed")
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                println("<<< Add")

               // val message = dataSnapshot?.getValue(Message::class.java)
              //  messageList?.add(message!!)

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

        }

        mMessageReference.addChildEventListener(childEventListener)

       // val recyclerView = note_list_recyclerview
     //   recyclerView.adapter = MainAdapter(messageReceived(), this)

    }

    @OnClick(R.id.button_send)
    fun sendMessage() {
        if(!input.text.isEmpty())
        FirebaseDatabase.getInstance()
                .getReference("message")
                .push()
                .setValue(input.text.toString())


        input.text.clear()
    }

}
