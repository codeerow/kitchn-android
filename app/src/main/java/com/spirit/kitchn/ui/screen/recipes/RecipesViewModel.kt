package com.spirit.kitchn.ui.screen.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.GetAllRecipesUseCase
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import com.spirit.kitchn.infrastructure.navigation.AppCoordinator
import com.spirit.kitchn.ui.component.item.recipe.RecipeItemVO
import com.spirit.kitchn.ui.mapping.toVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecipesViewModel(
    getAllRecipesUseCase: GetAllRecipesUseCase,
    private val coordinator: AppCoordinator,
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeItemVO>>(listOf())
    val recipes: StateFlow<List<RecipeItemVO>> = _recipes

    init {
        getAllRecipesUseCase.execute()
            .onEach { _recipes.emit(it.map(RecipeDTO::toVO)) }
            .launchIn(viewModelScope)
    }

    fun addRecipe() = coordinator.navigateToRecipeCreation()
    fun onItemClicked(id: String) = coordinator.navigateToRecipeDescription(id)
}