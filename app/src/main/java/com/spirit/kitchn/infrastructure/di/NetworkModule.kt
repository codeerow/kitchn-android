package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.BuildConfig
import com.spirit.kitchn.infrastructure.network.buildHttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.mock.MockEngine
import org.koin.dsl.module

val networkModule = module {
    factory<HttpClientEngine> {
        if (BuildConfig.FLAVOR != "stubbed") {
            Android.create()
        } else {
            MockEngine { request ->

            }
        }

    }
    single {
        buildHttpClient(
            engine = get(),
            tokenRepo = get(),
        )
    }
}

