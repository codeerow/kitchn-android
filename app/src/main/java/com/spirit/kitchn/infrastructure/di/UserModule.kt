package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.core.user.GetMyProductsUseCase
import com.spirit.kitchn.core.user.GetUserInfoUseCase
import org.koin.dsl.module

val userModule = module {
    factory {
        GetMyProductsUseCase(
            httpClient = get()
        )
    }
    factory {
        GetUserInfoUseCase(
            httpClient = get()
        )
    }
}