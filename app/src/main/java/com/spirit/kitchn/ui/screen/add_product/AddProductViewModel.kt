package com.spirit.kitchn.ui.screen.add_product

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddProductViewModel(
    private val barcode: String,
) : ViewModel() {

    val name = MutableStateFlow("")
//    val assets = MutableStateFlow<List<AssetInfo>>(listOf())
    val families = MutableStateFlow<List<String>>(listOf())

    fun deleteAsset() {

    }

    fun onAddProductClicked() {

    }
}

