package com.spirit.kitchn.ui.screen.add_product

import androidx.lifecycle.ViewModel
import com.spirit.kitchn.ui.component.PhotoItem
import kotlinx.coroutines.flow.MutableStateFlow

class AddProductViewModel(
    private val barcode: String,
) : ViewModel() {

    val photos = MutableStateFlow(listOf<PhotoItem.Photo>())
    val name = MutableStateFlow("")

    //    val assets = MutableStateFlow<List<AssetInfo>>(listOf())
    val families = MutableStateFlow<List<String>>(listOf())

    fun deleteAsset() {

    }

    fun onAddProductClicked() {

    }
}

