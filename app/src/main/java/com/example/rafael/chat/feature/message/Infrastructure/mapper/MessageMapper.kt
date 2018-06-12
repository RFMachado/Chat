package com.example.rafael.chat.feature.message.Infrastructure.mapper

import com.example.rafael.chat.feature.message.Infrastructure.models.MessagePayload
import com.example.rafael.chat.feature.message.domain.entities.Message

/**
 * Created by Rafael on 16/03/2018.
 */
object MessageMapper {

    fun map(payload: MessagePayload): Message {
            return Message(payload.text, payload.nickName, payload.userId)
    }

}