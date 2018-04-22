package com.example.rafael.chat.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import butterknife.BindView

import butterknife.ButterKnife
import butterknife.OnClick

import com.example.rafael.chat.R
import com.example.rafael.chat.domain.Message
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @BindView(R.id.edit_text)
    lateinit var input: EditText

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    lateinit var messageList: ArrayList<String>

    lateinit var childEventListener: ChildEventListener
    lateinit var mMessageReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val list = arrayListOf(" ")
        messageList = list

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

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

                val message = dataSnapshot?.getValue(String::class.java)
                messageList.add(message ?: " ")
                println("Add : $message")
                recyclerView.adapter.notifyDataSetChanged()

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

        }

        mMessageReference.addChildEventListener(childEventListener)


        recyclerView.adapter = MainAdapter(messageList)
        recyclerView.adapter.notifyDataSetChanged()
    }

    @OnClick(R.id.button_send)
    fun sendMessage() {
        if (!input.text.isEmpty())
            FirebaseDatabase.getInstance()
                    .getReference("message")
                    .push()
                    .setValue(input.text.toString())


        input.text.clear()

    }

    private fun setupRecycler() = with(recyclerView) {


    }

}
