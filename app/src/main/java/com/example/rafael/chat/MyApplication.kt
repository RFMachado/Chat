package com.example.rafael.chat

import android.app.Application
import com.example.rafael.chat.dagger.component.DaggerAppComponent
import com.example.rafael.chat.dagger.component.AppComponent
import com.example.rafael.chat.dagger.module.AppModule

class MyApplication: Application() {

    companion object {
        lateinit var coreComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()

        coreComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}