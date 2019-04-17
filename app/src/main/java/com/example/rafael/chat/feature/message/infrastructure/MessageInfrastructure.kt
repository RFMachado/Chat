package com.example.rafael.chat.feature.message.infrastructure

import com.example.rafael.chat.feature.message.infrastructure.mapper.MessageMapper
import com.example.rafael.chat.feature.message.infrastructure.models.MessagePayload
import com.example.rafael.chat.feature.message.domain.MessageSource
import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.UserPref
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import javax.inject.Inject

class MessageInfrastructure @Inject constructor(private val userPref: UserPref): MessageSource {

    override fun fetchMessage(): Observable<Message> {
        val channelName = userPref.getString(Consts.CHANNEL) ?: "Global 1"
        val mMessageReference = FirebaseDatabase.getInstance().getReference(Consts.CHANNEL).child(channelName)

        return Observable.create { emitter ->

            val childEventListener = object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) { }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) { }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) { }

                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    val message = dataSnapshot.getValue(MessagePayload::class.java) ?: return

                    if (!emitter.isDisposed) {
                        emitter.onNext(MessageMapper.map(message))
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) { }
            }

            mMessageReference.addChildEventListener(childEventListener)

            emitter.setCancellable {
                mMessageReference.removeEventListener(childEventListener)
            }
        }

    }
}