package com.spirit.kitchn.infrastructure.di.core

import com.spirit.kitchn.core.product.AddProductManuallyUseCase
import com.spirit.kitchn.core.product.GetProductDetailsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val productModule = module {
    factory {
        AddProductManuallyUseCase(
            context = androidContext(),
            httpClient = get(),
        )
    }

    factory {
        GetProductDetailsUseCase(
            httpClient = get(),
        )
    }
}