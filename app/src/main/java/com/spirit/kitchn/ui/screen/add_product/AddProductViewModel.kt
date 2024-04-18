package com.spirit.kitchn.ui.screen.add_product

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddProductViewModel(
    private val barcode: String,
) : ViewModel() {

    val name = MutableStateFlow("")

    fun onAddProductClicked() {

    }
}

