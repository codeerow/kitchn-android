package com.spirit.kitchn.ui.screen.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf


const val PANTRY_ROUTE = "HOME_ROUTE"

fun NavGraphBuilder.pantryScreen(
    navigateToProductCreation: (String) -> Unit,
    navigateToError: (String) -> Unit,
) {
    composable(
        route = PANTRY_ROUTE,
    ) {
        val viewModel: PantryViewModel = koinNavViewModel {
            parametersOf(
                navigateToProductCreation, navigateToError,
            )
        }

        val products by viewModel.products.collectAsState()

        PantryScreen(
            products = products,
            onAddProductClicked = viewModel::onAddProductClicked,
            onItemClicked = {},
            onProductSwiped = viewModel::onDeleteProductClicked,
        )
    }
}
