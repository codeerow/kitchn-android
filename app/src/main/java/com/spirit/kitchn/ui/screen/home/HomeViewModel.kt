package com.spirit.kitchn.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.user.GetMyProductsUseCase
import com.spirit.kitchn.core.user.GetUserInfoUseCase
import com.spirit.kitchn.core.user.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getMyProductsUseCase: GetMyProductsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products: StateFlow<List<Product>> = _products

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    init {
        viewModelScope.launch {
            _products.emit(getMyProductsUseCase.execute())
        }
        viewModelScope.launch {
            val user = getUserInfoUseCase.execute()
            _title.emit(user.username)
        }
    }
}

