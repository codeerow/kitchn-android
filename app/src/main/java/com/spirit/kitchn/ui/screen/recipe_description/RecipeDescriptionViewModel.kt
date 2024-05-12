package com.spirit.kitchn.ui.screen.recipe_description

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.DeleteRecipeUseCase
import com.spirit.kitchn.core.recipe.GetRecipeDescriptionUseCase
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDescriptionViewModel(
    private val recipeIdArg: String,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    getRecipeDescriptionUseCase: GetRecipeDescriptionUseCase,
) : ViewModel() {

    val navigation = MutableSharedFlow<Navigation>()

    private val _recipe = MutableStateFlow<RecipeDTO?>(null)
    val recipe: StateFlow<RecipeDTO?> = _recipe

    init {
        viewModelScope.launch {
            _recipe.emit(getRecipeDescriptionUseCase.execute(recipeIdArg))
        }
    }

    fun deleteRecipe() {
        viewModelScope.launch {
            deleteRecipeUseCase.execute(recipeId = recipeIdArg)
            navigation.emit(Navigation.RecipeDeleted)
        }
    }

    sealed interface Navigation {
        data object RecipeDeleted : Navigation
    }
}