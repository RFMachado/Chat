package com.example.rafael.chat.dagger.module

import android.app.Application
import android.content.Context
import com.example.rafael.chat.shared.UserPref
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var mApplication: Application) {

    @Provides
    fun provideApplication() =  mApplication

    @Provides
    fun provideContext() =  mApplication.applicationContext

    @Provides
    @Singleton
    fun provideUserPref(context: Context) = UserPref(context)
}