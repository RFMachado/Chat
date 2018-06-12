package com.example.rafael.chat.dagger.component

import com.example.rafael.chat.dagger.module.AppModule
import com.example.rafael.chat.dagger.module.MessageModule
import com.example.rafael.chat.feature.login.ui.LoginActivity
import com.example.rafael.chat.feature.message.ui.MessageActivity
import com.example.rafael.chat.feature.nickname.ui.NickNameActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MessageModule::class])

interface AppComponent {
    fun inject(activity: MessageActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: NickNameActivity)
}
