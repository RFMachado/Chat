package com.example.rafael.chat.feature.privatemessage.presentation

import com.example.rafael.chat.feature.message.domain.entities.Message

interface PrivateMessageView {
    fun showMessage(message: Message)
    fun showError()

    fun removeAllItems()
}