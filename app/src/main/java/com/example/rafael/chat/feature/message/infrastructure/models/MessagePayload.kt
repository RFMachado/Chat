package com.example.rafael.chat.feature.message.infrastructure.models

/**
 * Created by Rafael on 21/02/2018.
 */
class MessagePayload(
        val text: String,
        val userId: String,
        val nickName: String
){
    constructor() : this("", "", "")
}