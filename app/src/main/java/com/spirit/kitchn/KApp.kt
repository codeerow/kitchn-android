package com.spirit.kitchn

import android.app.Application
import com.spirit.kitchn.infrastructure.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KApp)
            modules(appModule)
        }
    }
}