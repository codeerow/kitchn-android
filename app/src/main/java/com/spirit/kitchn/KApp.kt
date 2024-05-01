package com.spirit.kitchn

import android.app.Application
import com.spirit.kitchn.infrastructure.di.authModule
import com.spirit.kitchn.infrastructure.di.recipeModule
import com.spirit.kitchn.infrastructure.di.uiModule
import com.spirit.kitchn.infrastructure.di.userModule
import com.spirit.kitchn.infrastructure.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KApp)
            modules(
                uiModule +
                    authModule +
                    userModule +
                    recipeModule +
                    networkModule
            )
        }
    }
}