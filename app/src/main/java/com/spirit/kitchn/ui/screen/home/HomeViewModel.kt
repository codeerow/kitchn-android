package com.spirit.kitchn.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.user.AddProductUseCase
import com.spirit.kitchn.core.user.DeleteProductUseCase
import com.spirit.kitchn.core.user.GetMyProductsUseCase
import com.spirit.kitchn.core.user.GetUserInfoUseCase
import com.spirit.kitchn.core.user.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    getMyProductsUseCase: GetMyProductsUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products: StateFlow<List<Product>> = _products

    private val _navigation = MutableSharedFlow<AddProductUseCase.Result>()
    val navigation: SharedFlow<AddProductUseCase.Result> = _navigation

    init {
        getMyProductsUseCase.execute()
            .onEach(_products::emit)
            .launchIn(viewModelScope)
    }

    fun onAddProductClicked() {
        viewModelScope.launch {
            val result = addProductUseCase.execute()
            _navigation.emit(result)
        }
    }

    fun onDeleteProductClicked(productId: String) {
        viewModelScope.launch {
            deleteProductUseCase.execute(productId = productId)
        }
    }
}

