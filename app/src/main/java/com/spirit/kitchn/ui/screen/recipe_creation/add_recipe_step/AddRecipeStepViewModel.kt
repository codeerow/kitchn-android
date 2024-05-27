package com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.CreateRecipeUseCase
import com.spirit.kitchn.ui.component.photos_grid.PhotoItem
import com.spirit.kitchn.ui.screen.recipe_creation.RecipeCreationCoordinator
import com.spirit.kitchn.utils.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddRecipeStepViewModel(
    private val coordinator: RecipeCreationCoordinator,
    private val step: CreateRecipeUseCase.Request,
) : ViewModel() {

    init {
        viewModelScope.launch {
            request.emit(
                request.value.copy(
                    steps = request.value.steps.apply {
                        add(curStepNumber, CreateRecipeUseCase.Request.Step())
                    }
                )
            )
        }
    }

    val description = request.map(viewModelScope) { it.steps[curStepNumber].description }
    val ingredients = request.map(viewModelScope) {
        it.steps[curStepNumber].ingredients.firstOrNull() ?: ""
    }
    val preview = request.map(viewModelScope) {
        it.steps[curStepNumber].preview?.let {
            PhotoItem.Photo(url = it.toString())
        } ?: PhotoItem.AddPhotoItem
    }

    fun createRecipe() {
        coordinator.createRecipe(request.value)
    }

    fun addPreview(uri: Uri) {
        viewModelScope.launch {
            request.emit(
                request.value.copy(
                    steps = request.value.steps.apply {
                        getOrNull(curStepNumber) ?: return@apply
                        this[curStepNumber] = this[curStepNumber].copy(preview = uri)
                    }
                )
            )
        }
    }

    fun addRecipeStep() = coordinator.addStep(curStepNumber)

    fun onBackPressed() {
        viewModelScope.launch {
            request.emit(
                request.value.copy(
                    steps = request.value.steps.apply {
                        removeAt(curStepNumber)
                    }
                )
            )
            coordinator.navigateBackward()
        }
    }

    fun changeIngredients(ingredients: String) {
        viewModelScope.launch {
            request.emit(
                request.value.copy(
                    steps = request.value.steps.toMutableList().apply {
                        this[curStepNumber] =
                            this[curStepNumber].copy(ingredients = listOf(ingredients))
                    }
                )
            )
        }
    }

    fun changeDescription(description: String) {
        viewModelScope.launch {
            request.emit(
                request.value.copy(
                    steps = request.value.steps.toMutableList().apply {
                        this[curStepNumber] = this[curStepNumber].copy(description = description)
                    }
                )
            )
        }
    }
}
