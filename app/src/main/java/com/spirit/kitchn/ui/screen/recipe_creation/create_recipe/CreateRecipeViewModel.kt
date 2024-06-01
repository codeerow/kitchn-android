package com.spirit.kitchn.ui.screen.recipe_creation.create_recipe

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.CreateRecipeUseCase
import com.spirit.kitchn.ui.component.PhotoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateRecipeViewModel(
    private val createRecipeUseCase: CreateRecipeUseCase,
    private val recipeCreationRequest: CreateRecipeUseCase.Request,
    private val onRecipeCreated: () -> Unit,
    private val onAddRecipeStep: () -> Unit,
    private val onCleared: () -> Unit,
) : ViewModel() {
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
            onRecipeCreated()
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

    fun addRecipeStep() {
        viewModelScope.launch {
            recipeCreationRequest.name = name.value
            recipeCreationRequest.description = description.value
            recipeCreationRequest.previewImage = preview.value?.url?.toUri()
            onAddRecipeStep()
        }
    }

    override fun onCleared() {
        super.onCleared()
        onCleared.invoke()
    }
}
