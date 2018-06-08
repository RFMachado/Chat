package com.example.rafael.chat

import android.app.Application
import com.example.rafael.chat.dagger.component.DaggerNetComponent
import com.example.rafael.chat.dagger.component.NetComponent
import com.example.rafael.chat.dagger.module.AppModule

class MyApplication: Application() {

    companion object {
        lateinit var netComponent: NetComponent
    }


    override fun onCreate() {
        super.onCreate()

        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}