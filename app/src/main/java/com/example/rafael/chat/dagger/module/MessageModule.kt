package com.example.rafael.chat.dagger.module

import com.example.rafael.chat.feature.message.Infrastructure.MessageInfrastructure
import com.example.rafael.chat.feature.message.domain.MessageSource
import dagger.Module
import dagger.Provides

@Module
class MessageModule {

    @Provides
    fun providesMessageSource(messageInfrastructure: MessageInfrastructure): MessageSource = messageInfrastructure

}