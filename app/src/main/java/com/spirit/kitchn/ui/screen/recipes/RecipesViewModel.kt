package com.spirit.kitchn.ui.screen.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.GetAllRecipesUseCase
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecipesViewModel(
    getAllRecipesUseCase: GetAllRecipesUseCase,
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeDTO>>(listOf())
    val recipes: StateFlow<List<RecipeDTO>> = _recipes

    init {
        getAllRecipesUseCase.execute()
            .onEach { _recipes.emit(it) }
            .launchIn(viewModelScope)
    }
}