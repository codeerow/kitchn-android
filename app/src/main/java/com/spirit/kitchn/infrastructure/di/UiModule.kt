package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.ui.screen.add_product.AddProductViewModel
import com.spirit.kitchn.ui.screen.home.HomeViewModel
import com.spirit.kitchn.ui.screen.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        WelcomeViewModel(
            loginUseCase = get(),
        )
    }
    viewModel {
        HomeViewModel(
            getMyProductsUseCase = get(),
            addProductUseCase = get(),
            deleteProductUseCase = get(),
        )
    }

    viewModel { (barcode: String) ->
        AddProductViewModel(
            barcode = barcode
        )
    }
}