package com.spirit.kitchn.ui.screen.home

import androidx.lifecycle.ViewModel
import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.core.user.product.usecases.DeleteProductUseCase
import com.spirit.kitchn.core.user.product.usecases.GetMyProductsUseCase
import com.spirit.kitchn.core.user.product.usecases.add_product.AddProductUseCase
import com.spirit.kitchn.ui.component.item.product.ProductItemVO
import com.spirit.kitchn.ui.mapping.toVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private val navigateToProductCreation: (String) -> Unit,
    private val navigateToError: (String) -> Unit,
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductItemVO>>(listOf())
    val products: StateFlow<List<ProductItemVO>> = _products
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        getMyProductsUseCase.execute()
            .map { it.map(ProductDTO::toVO) }
            .onEach(_products::emit)
            .launchIn(coroutineScope)
    }

    fun onAddProductClicked() {
        coroutineScope.launch {
            when (val result = addProductUseCase.execute()) {
                is AddProductUseCase.Result.Failure.ProductNotFound -> {
                    navigateToProductCreation(result.barcode)
                }

                AddProductUseCase.Result.Failure.ScanFailed -> {
                    navigateToError("Error during scanning")
                }

                AddProductUseCase.Result.Success -> {

                }
            }
        }
    }

    fun onDeleteProductClicked(productId: String) {
        coroutineScope.launch {
            deleteProductUseCase.execute(productId = productId)
        }
    }
}
