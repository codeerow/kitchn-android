package com.spirit.kitchn.infrastructure.di

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.spirit.kitchn.core.user.AddProductUseCase
import com.spirit.kitchn.core.user.DeleteProductUseCase
import com.spirit.kitchn.core.user.GetMyProductsUseCase
import com.spirit.kitchn.core.user.GetUserInfoUseCase
import com.spirit.kitchn.core.user.datasource.ProductDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback
import org.koin.dsl.module

val userModule = module {
    single {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_ALL_FORMATS,
            )
            .enableAutoZoom()
            .build()
        GmsBarcodeScanning.getClient(androidContext(), options)
    }

    single {
        val coroutineScope = CoroutineScope(Job())
        registerCallback(object : ScopeCallback {
            override fun onScopeClose(scope: Scope) {
                coroutineScope.cancel()
            }
        })

        ProductDataSource(
            httpClient = get(),
            coroutineScope = coroutineScope
        )
    }

    factory {
        GetMyProductsUseCase(
            dataSource = get()
        )
    }

    factory {
        GetUserInfoUseCase(
            httpClient = get()
        )
    }

    factory {
        AddProductUseCase(
            scanner = get(),
            dataSource = get(),
            httpClient = get(),
        )
    }

    factory {
        DeleteProductUseCase(
            dataSource = get(),
            httpClient = get(),
        )
    }
}