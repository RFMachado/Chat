package com.example.rafael.chat.feature.message.presentation

import com.example.rafael.chat.feature.message.domain.entities.Message

interface MessageView {
    fun showMessage(message: Message)
    fun showError()
}