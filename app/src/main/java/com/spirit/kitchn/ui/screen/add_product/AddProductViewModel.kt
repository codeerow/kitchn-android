package com.spirit.kitchn.ui.screen.add_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.BuildConfig
import com.spirit.kitchn.core.user.AddProductUseCase
import com.spirit.kitchn.core.user.GetMyProductsUseCase
import com.spirit.kitchn.core.user.GetUserInfoUseCase
import com.spirit.kitchn.core.user.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val barcode: String,
) : ViewModel() {

    val name = MutableStateFlow("")

    fun onAddProductClicked() {

    }
}

