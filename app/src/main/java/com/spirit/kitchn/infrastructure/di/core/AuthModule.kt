package com.spirit.kitchn.infrastructure.di.core

import com.spirit.kitchn.core.auth.LoginUseCase
import com.spirit.kitchn.core.auth.repo.TokenRepo
import com.spirit.kitchn.core.auth.repo.TokensManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {
    single {
        TokenRepo(
            tokensManager = TokensManager(
                context = androidContext(),
            ),
        )
    }
    factory {
        LoginUseCase(
            httpClient = get(),
            tokenRepo = get(),
        )
    }
}