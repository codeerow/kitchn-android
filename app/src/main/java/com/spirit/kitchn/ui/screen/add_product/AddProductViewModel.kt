package com.spirit.kitchn.ui.screen.add_product

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.product.AddProductManuallyUseCase
import com.spirit.kitchn.ui.component.photos_grid.PhotoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val barcode: String,
    private val addProductManuallyUseCase: AddProductManuallyUseCase,
) : ViewModel() {

    val photos = MutableStateFlow(listOf<PhotoItem.Photo>())
    val name = MutableStateFlow("")
    val productFamily = MutableStateFlow("")


    fun addAsset(uri: Uri) {
        photos.tryEmit(
            photos.value.toMutableList() + PhotoItem.Photo(
                id = (0..Int.MAX_VALUE).random(),
                url = uri.toString(),
            )
        )
    }

    fun onAddProductClicked() {
        viewModelScope.launch {
            val request = AddProductManuallyUseCase.Request(
                barcode = barcode,
                uris = photos.value.map { it.url.toUri() },
                name = name.value,
                productFamily = productFamily.value
            )
            addProductManuallyUseCase.execute(request)
        }
    }

    fun deleteAsset(id: Int) {
        val newAssets = photos.value.toMutableList().apply {
            removeIf { it.id == id }
        }
        photos.tryEmit(newAssets)
    }
}

