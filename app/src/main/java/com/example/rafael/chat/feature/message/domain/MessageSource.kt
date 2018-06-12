package com.example.rafael.chat.feature.message.domain

import com.example.rafael.chat.feature.message.domain.entities.Message
import io.reactivex.Observable

interface MessageSource {
    fun fetchMessage(): Observable<Message>
}