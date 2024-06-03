package com.spirit.kitchn.ui.screen.add_product

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
data class AddProductManually(val barcode: String)

fun NavGraphBuilder.addProductScreen(
    onProductAdded: () -> Unit,
) {
    composable<AddProductManually> { backStackEntry ->
        val addProductManually: AddProductManually = backStackEntry.toRoute()
        val viewModel: AddProductViewModel =
            koinNavViewModel { parametersOf(addProductManually.barcode, onProductAdded) }

        val name by viewModel.name.collectAsState()
        val productFamily by viewModel.productFamily.collectAsState()
        val photos by viewModel.photos.collectAsState()

        AddProductScreen(
            name = name,
            photos = photos,
            productFamily = productFamily,
            onAddAsset = viewModel::addAsset,
            onDeleteAsset = viewModel::deleteAsset,
            onNameChanged = viewModel.name::tryEmit,
            onAddProductClicked = viewModel::onAddProductClicked,
            onProductFamilyChanged = viewModel.productFamily::tryEmit,
        )
    }
}

fun NavController.navigateToAddProductScreen(barcode: String) {
    navigate(AddProductManually(barcode = barcode))
}