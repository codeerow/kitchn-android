package com.spirit.kitchn.ui.screen.recipe_creation.create_recipe

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.CreateRecipeUseCase
import com.spirit.kitchn.ui.component.photos_grid.PhotoItem
import com.spirit.kitchn.ui.screen.recipe_creation.RecipeCreationCoordinator
import com.spirit.kitchn.utils.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateRecipeViewModel(
    private val request: MutableStateFlow<CreateRecipeUseCase.Request>,
    private val onCleared: () -> Unit,
    private val coordinator: RecipeCreationCoordinator,
) : ViewModel() {

    val name = request.map(viewModelScope) { it.name }
    val description = request.map(viewModelScope) { it.description }
    val preview = request.map(viewModelScope) {
        it.previewImage?.let { PhotoItem.Photo(url = it.toString()) } ?: PhotoItem.AddPhotoItem
    }

    fun createRecipe() {
        coordinator.createRecipe(request.value)
    }

    fun addPreview(uri: Uri) {
        viewModelScope.launch {
            request.emit(
                request.value.copy(
                    previewImage = uri,
                )
            )
        }
    }

    fun addRecipeStep() {
        coordinator.addStep(curStepNumber = 0)
    }

    fun changeName(name: String) {
        viewModelScope.launch {
            request.emit(request.value.copy(name = name))
        }
    }

    fun changeDescription(description: String) {
        viewModelScope.launch {
            request.emit(request.value.copy(description = description))
        }
    }

    override fun onCleared() {
        super.onCleared()
        onCleared.invoke()
    }
}
