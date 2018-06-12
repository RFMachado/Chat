package com.example.rafael.chat.feature.message.domain.entities

data class Message(
    var text: String,
    var nickName: String,
    var userId: String,
    var isMyUser: Boolean = false
)