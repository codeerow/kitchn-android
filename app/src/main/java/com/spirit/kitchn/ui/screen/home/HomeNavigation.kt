package com.spirit.kitchn.ui.screen.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf


const val HOME_ROUTE = "HOME_ROUTE"

fun NavGraphBuilder.homeScreen(
    showAllRecipes: () -> Unit,
    navigateToProductCreation: (String) -> Unit,
    navigateToError: (String) -> Unit,
) {
    composable(
        route = HOME_ROUTE,
    ) {
        val viewModel: HomeViewModel = koinNavViewModel {
            parametersOf(
                navigateToProductCreation, navigateToError,
            )
        }
        val products by viewModel.products.collectAsState()

        HomeScreen(
            products = products,
            onAddProductClicked = viewModel::onAddProductClicked,
            onItemClicked = viewModel::onDeleteProductClicked,
            onShowAllRecipesClicked = showAllRecipes,
        )
    }
}

fun NavController.navigateToHome() {
    navigate(HOME_ROUTE)
}