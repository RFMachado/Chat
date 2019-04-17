package com.example.rafael.chat

import android.app.Application
import com.example.rafael.chat.dagger.component.DaggerAppComponent
import com.example.rafael.chat.dagger.component.AppComponent
import com.example.rafael.chat.dagger.module.AppModule
import timber.log.Timber

class MyApplication: Application() {

    companion object {
        lateinit var coreComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format("[%s] (%s:%s)",
                            super.createStackElementTag(element),
                            element.methodName,
                            element.lineNumber)
                }
            })
        }

        coreComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}