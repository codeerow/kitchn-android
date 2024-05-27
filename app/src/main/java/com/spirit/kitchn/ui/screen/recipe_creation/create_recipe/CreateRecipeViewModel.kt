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
    recipeMainInfo: MutableStateFlow<CreateRecipeUseCase.Request.RecipeMainInfo>,
    private val coordinator: RecipeCreationCoordinator,
) {

    val name = recipeMainInfo.map(viewModelScope) { it.name }
    val description = recipeMainInfo.map(viewModelScope) { it.description }
    val preview = recipeMainInfo.map(viewModelScope) {
        it.previewImage?.let { PhotoItem.Photo(url = it.toString()) } ?: PhotoItem.AddPhotoItem
    }

    fun createRecipe() {
        coordinator.createRecipe()
    }

    fun addPreview(uri: Uri) {
        viewModelScope.launch {
            recipeMainInfo.emit(
                request.value.copy(
                    previewImage = uri,
                )
            )
        }
    }

    fun addRecipeStep() {
        coordinator.addStep()
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
}
