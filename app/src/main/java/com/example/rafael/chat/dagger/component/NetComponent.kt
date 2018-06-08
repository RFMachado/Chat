package com.example.rafael.chat.dagger.component

import com.example.rafael.chat.dagger.module.AppModule
import com.example.rafael.chat.feature.message.ui.MessageActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])

interface NetComponent {
    fun inject(activity: MessageActivity)
}
