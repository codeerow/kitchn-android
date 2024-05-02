package com.spirit.kitchn.ui.screen.recipe_creation.create_recipe

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.CreateRecipeUseCase
import com.spirit.kitchn.ui.component.PhotoItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateRecipeViewModel(
    private val createRecipeUseCase: CreateRecipeUseCase,
) : ViewModel() {
    val navigation = MutableSharedFlow<Unit>()
    val name = MutableStateFlow("")
    val description = MutableStateFlow("")
    val preview = MutableStateFlow<PhotoItem.Photo?>(null)

    fun createRecipe() {
        viewModelScope.launch {
            val request = CreateRecipeUseCase.Request(
                name = name.value,
                description = description.value,
                previewImage = preview.value?.url?.toUri(),
            )
            createRecipeUseCase.execute(request)
            navigation.emit(Unit)
        }
    }

    fun addPreview(uri: Uri) {
        viewModelScope.launch {
            preview.emit(
                PhotoItem.Photo(
                    id = (0..Int.MAX_VALUE).random(),
                    url = uri.toString(),
                )
            )
        }
    }
}
