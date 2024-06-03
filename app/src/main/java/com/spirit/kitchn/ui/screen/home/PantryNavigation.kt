package com.spirit.kitchn.ui.screen.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object Pantry

fun NavGraphBuilder.pantryScreen(
    onAddProductClicked: (String) -> Unit,
    onProductClicked: (String) -> Unit,
    onError: (String) -> Unit,
) {
    composable<Pantry> {
        val viewModel: PantryViewModel = koinNavViewModel {
            parametersOf(
                onAddProductClicked, onError,
            )
        }

        val products by viewModel.products.collectAsState()

        PantryScreen(
            products = products,
            onAddProductClicked = viewModel::onAddProductClicked,
            onItemClicked = onProductClicked,
            onProductSwiped = viewModel::onDeleteProductClicked,
        )
    }
}
