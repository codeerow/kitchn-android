package com.spirit.kitchn.ui.screen.add_product

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.spirit.kitchn.ui.component.PhotoItem
import kotlinx.coroutines.flow.MutableStateFlow

class AddProductViewModel(
    private val barcode: String,
) : ViewModel() {

    val photos = MutableStateFlow(listOf<PhotoItem.Photo>())
    val name = MutableStateFlow("")
    val productFamily = MutableStateFlow("")

    fun deleteAsset(assetId: Int) {
        photos.tryEmit(
            photos.value.toMutableList().apply {
                removeIf { it.id == assetId }
            }
        )
    }

    fun addAsset(uri: Uri) {
        photos.tryEmit(
            photos.value.toMutableList() + PhotoItem.Photo(
                id = (0..Int.MAX_VALUE).random(),
                url = uri.toString(),
            )
        )
    }

    fun onAddProductClicked() {

    }
}

