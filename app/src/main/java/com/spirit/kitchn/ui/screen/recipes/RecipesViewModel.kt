package com.spirit.kitchn.ui.screen.recipes

import androidx.lifecycle.ViewModel
import com.spirit.kitchn.core.recipe.GetAllRecipesUseCase
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipesViewModel (
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeDTO>>(listOf())
    val recipes: StateFlow<List<RecipeDTO>> = _recipes
}