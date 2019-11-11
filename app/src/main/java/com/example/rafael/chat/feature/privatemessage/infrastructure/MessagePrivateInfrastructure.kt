package com.example.rafael.chat.feature.privatemessage.infrastructure

import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.feature.privatemessage.domain.MessagePrivateSource
import com.example.rafael.chat.feature.privatemessage.infrastructure.mapper.MessagePrivateMapper
import com.example.rafael.chat.feature.privatemessage.infrastructure.models.MessagePrivatePayload
import com.example.rafael.chat.shared.Consts
import com.example.rafael.chat.shared.UserPref
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import javax.inject.Inject

class MessagePrivateInfrastructure @Inject constructor(private val userPref: UserPref) : MessagePrivateSource {
    override fun fetchMessage(key: String): Observable<Message> {
        val mMessageReference = FirebaseDatabase.getInstance().getReference(Consts.PRIVATE).child(key)

        return Observable.create { emitter ->

            val childEventListener = object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) { }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) { }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) { }

                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    val message = dataSnapshot.getValue(MessagePrivatePayload::class.java) ?: return

                    if (!emitter.isDisposed) {
                        emitter.onNext(MessagePrivateMapper.map(message))
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