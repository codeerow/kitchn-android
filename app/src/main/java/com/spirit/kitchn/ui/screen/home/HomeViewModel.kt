package com.spirit.kitchn.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.core.user.product.usecases.DeleteProductUseCase
import com.spirit.kitchn.core.user.product.usecases.GetMyProductsUseCase
import com.spirit.kitchn.core.user.product.usecases.add_product.AddProductUseCase
import com.spirit.kitchn.infrastructure.navigation.AppCoordinator
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
    private val coordinator: AppCoordinator,
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
                    coordinator.navigateToProductCreation(result.barcode)
                }

                AddProductUseCase.Result.Failure.ScanFailed -> {
                    coordinator.navigateToError("Error during scanning")
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

    fun showAllRecipes() = coordinator.navigateToAllRecipes()
}
