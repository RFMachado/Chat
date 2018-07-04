package com.example.rafael.chat.feature.message.infrastructure.mapper

import com.example.rafael.chat.feature.message.infrastructure.models.MessagePayload
import com.example.rafael.chat.feature.message.domain.entities.Message

/**
 * Created by Rafael on 16/03/2018.
 */
object MessageMapper {

    fun map(payload: MessagePayload): Message {
            return Message(payload.text, payload.nickName, payload.userId)
    }

}