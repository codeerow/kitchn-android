package com.spirit.kitchn.ui.screen.add_product

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.user.product.AddProductManuallyUseCase
import com.spirit.kitchn.ui.component.PhotoItem
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
            addProductManuallyUseCase.execute(
                barcode = barcode,
                uris = photos.value.map { it.url.toUri() },
                name = name.value,
                productFamily = productFamily.value
            )
        }
    }

    fun deleteAsset(id: Int) {
//        photos.tryEmit(
//            photos.value.toMutableList().removeIf { it.id == id }
//        )
    }
}

