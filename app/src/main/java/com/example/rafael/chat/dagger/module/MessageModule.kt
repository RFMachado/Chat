package com.example.rafael.chat.dagger.module

import com.example.rafael.chat.feature.message.Infrastructure.MessageInfrastructure
import com.example.rafael.chat.feature.message.domain.MessageSource
import com.example.rafael.chat.feature.privatemessage.domain.MessagePrivateSource
import com.example.rafael.chat.feature.privatemessage.infrastructure.MessagePrivateInfrastructure
import dagger.Module
import dagger.Provides

@Module
class MessageModule {

    @Provides
    fun providesMessageSource(messageInfrastructure: MessageInfrastructure): MessageSource = messageInfrastructure

    @Provides
    fun providesPrivateMessageSource(messagePrivateInfrastructure: MessagePrivateInfrastructure): MessagePrivateSource = messagePrivateInfrastructure

}