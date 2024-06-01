package com.spirit.kitchn.ui.screen.add_product

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

const val BARCODE_ARG = "BARCODE_ARG"
const val PRODUCT_NOT_FOUND_ROUTE = "PRODUCT_NOT_FOUND_ROUTE/{$BARCODE_ARG}"

fun NavGraphBuilder.addProductScreen(
    onProductAdded: () -> Unit,
) {
    composable(
        route = PRODUCT_NOT_FOUND_ROUTE,
        arguments = listOf(navArgument(BARCODE_ARG) { type = NavType.StringType })
    ) { backStackEntry ->
        val barcodeArg = backStackEntry.arguments?.getString(BARCODE_ARG) ?: ""
        val viewModel: AddProductViewModel =
            koinNavViewModel { parametersOf(barcodeArg, onProductAdded) }

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
    val route = PRODUCT_NOT_FOUND_ROUTE.replace(
        oldValue = "{$BARCODE_ARG}",
        newValue = barcode,
    )
    navigate(route)
}