package com.spirit.kitchn.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.core.user.product.usecases.DeleteProductUseCase
import com.spirit.kitchn.core.user.product.usecases.GetMyProductsUseCase
import com.spirit.kitchn.core.user.product.usecases.add_product.AddProductUseCase
import com.spirit.kitchn.infrastructure.navigation.BARCODE_ARG
import com.spirit.kitchn.infrastructure.navigation.ERROR_DESCRIPTION_ARG
import com.spirit.kitchn.infrastructure.navigation.ERROR_ROUTE
import com.spirit.kitchn.infrastructure.navigation.PRODUCT_NOT_FOUND_ROUTE
import com.spirit.kitchn.ui.component.item.product.ProductItemVO
import com.spirit.kitchn.ui.mapping.toVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    getMyProductsUseCase: GetMyProductsUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val navHostController: NavHostController,
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductItemVO>>(listOf())
    val products: StateFlow<List<ProductItemVO>> = _products

    init {
        getMyProductsUseCase.execute()
            .map { it.map(ProductDTO::toVO) }
            .onEach(_products::emit)
            .launchIn(viewModelScope)
    }

    fun onAddProductClicked() {
        viewModelScope.launch {
            when (val result = addProductUseCase.execute()) {
                is AddProductUseCase.Result.Failure.ProductNotFound -> {
                    val route = PRODUCT_NOT_FOUND_ROUTE.replace(
                        oldValue = "{$BARCODE_ARG}",
                        newValue = result.barcode,
                    )
                    navHostController.navigate(route)
                }

                AddProductUseCase.Result.Failure.ScanFailed -> {
                    val route = ERROR_ROUTE.replace(
                        oldValue = "{$ERROR_DESCRIPTION_ARG}",
                        newValue = "Error during scanning",
                    )
                    navHostController.navigate(route)
                }

                AddProductUseCase.Result.Success -> {}
            }
        }
    }

    fun onDeleteProductClicked(productId: String) {
        viewModelScope.launch {
            deleteProductUseCase.execute(productId = productId)
        }
    }
}

