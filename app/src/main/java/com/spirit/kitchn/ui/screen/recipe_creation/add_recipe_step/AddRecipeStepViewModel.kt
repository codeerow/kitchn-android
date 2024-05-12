package com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.CreateRecipeUseCase
import com.spirit.kitchn.ui.component.PhotoItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddRecipeStepViewModel(
    private val recipeCreationRequest: CreateRecipeUseCase.Request,
    private val createRecipeUseCase: CreateRecipeUseCase,
) : ViewModel() {
    val navigation = MutableSharedFlow<Navigation>()
    val ingredients = MutableStateFlow("")
    val description = MutableStateFlow("")
    val preview = MutableStateFlow<PhotoItem.Photo?>(null)
    
    fun createRecipe() {
        val step = CreateRecipeUseCase.Request.Step(
            description = description.value,
            ingredients = listOf(ingredients.value),
        )
        recipeCreationRequest.steps = mutableListOf(step) + recipeCreationRequest.steps

        viewModelScope.launch {
            createRecipeUseCase.execute(recipeCreationRequest)
            navigation.emit(Navigation.RecipeCreated)
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
            val step = CreateRecipeUseCase.Request.Step(
                description = description.value,
                ingredients = listOf(ingredients.value),
            )
            recipeCreationRequest.steps = mutableListOf(step) + recipeCreationRequest.steps
            navigation.emit(Navigation.AddRecipeStep)
        }
    }

    sealed interface Navigation {
        data object AddRecipeStep : Navigation
        data object RecipeCreated : Navigation
    }
}
