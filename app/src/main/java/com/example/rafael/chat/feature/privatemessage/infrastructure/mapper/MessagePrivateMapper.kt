package com.example.rafael.chat.feature.privatemessage.infrastructure.mapper

import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.feature.privatemessage.infrastructure.models.MessagePrivatePayload

object MessagePrivateMapper {
    fun map(payload: MessagePrivatePayload): Message {
        return Message(payload.text, payload.nickName, payload.userId)
    }
}