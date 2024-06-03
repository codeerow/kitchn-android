package com.spirit.kitchn.ui.screen.product_details

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class ProductDetails(val productId: String)

fun NavGraphBuilder.productDetailsScreen() {
    composable<ProductDetails> { backStackEntry ->
        val productDetails: ProductDetails = backStackEntry.toRoute()

        val viewModel: ProductDetailsViewModel = koinNavViewModel {
            parametersOf(productDetails.productId)
        }

        val state by viewModel.state.collectAsState()

        ProductDetailsScreen(
            state = state,
        )
    }
}

fun NavController.navigateToProductDetails(productId: String) {
    navigate(ProductDetails(productId = productId))
}