package com.spirit.kitchn.ui.screen.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spirit.kitchn.core.recipe.DeleteRecipeUseCase
import com.spirit.kitchn.core.recipe.GetRecipeDetailsUseCase
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import com.spirit.kitchn.ui.component.item.recipe_step.StepItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val recipeIdArg: String,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val onDeleted: () -> Unit,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            val content = getRecipeDetailsUseCase.execute(recipeIdArg).toContent()
            _state.emit(content)
        }
    }

    fun deleteRecipe() {
        viewModelScope.launch {
            deleteRecipeUseCase.execute(recipeId = recipeIdArg)
            onDeleted()
        }
    }

    private fun RecipeDTO.toContent(): State.Content {
        return State.Content(
            title = this.name,
            headerImageUrl = this.images.firstOrNull()?.url,
            description = this.description,
            ingredients = steps.flatMap { it.productFamilies.map { it.title } },
            steps = steps.map {
                StepItemVO(
                    description = it.description,
                    ingredients = it.productFamilies.joinToString { it.title }
                )
            }
        )
    }

    sealed class State {
        abstract val title: String

        data object Loading : State() {
            override val title: String = "Loading"
        }

        data class Content(
            override val title: String,
            val headerImageUrl: String?,
            val description: String,
            val ingredients: List<String>,
            val steps: List<StepItemVO>,
        ) : State()
    }
}