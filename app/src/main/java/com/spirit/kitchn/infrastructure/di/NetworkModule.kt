package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.infrastructure.network.buildHttpClient
import org.koin.dsl.module

val networkModule = module {
    single {
        buildHttpClient(
            tokenRepo = get(),
            rootNavController = get(),
        )
    }
}

