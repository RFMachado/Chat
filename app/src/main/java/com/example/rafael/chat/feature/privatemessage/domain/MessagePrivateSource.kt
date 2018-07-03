package com.example.rafael.chat.feature.privatemessage.domain

import com.example.rafael.chat.feature.message.domain.entities.Message
import io.reactivex.Observable

interface MessagePrivateSource {
    fun fetchMessage(key: String): Observable<Message>
}