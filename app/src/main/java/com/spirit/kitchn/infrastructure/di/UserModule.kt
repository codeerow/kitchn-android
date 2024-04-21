package com.spirit.kitchn.infrastructure.di

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.spirit.kitchn.core.user.personal_info.GetUserInfoUseCase
import com.spirit.kitchn.core.user.product.AddProductUseCase
import com.spirit.kitchn.core.user.product.DeleteProductUseCase
import com.spirit.kitchn.core.user.product.GetMyProductsUseCase
import com.spirit.kitchn.core.user.product.datasource.ProductDataSource
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
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_UPC_E,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_CODE_128,
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
            context = androidContext(),
        )
    }

    factory {
        DeleteProductUseCase(
            dataSource = get(),
            httpClient = get(),
        )
    }
}