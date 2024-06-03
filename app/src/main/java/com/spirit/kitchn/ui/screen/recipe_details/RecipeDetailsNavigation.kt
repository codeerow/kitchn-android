package com.spirit.kitchn.ui.screen.recipe_details

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class RecipeDetails(val recipeId: String)

fun NavGraphBuilder.recipeDetailsScreen(
    onRecipeDeleted: () -> Unit,
) {
    composable<RecipeDetails> { backStackEntry ->
        val recipeDetails: RecipeDetails = backStackEntry.toRoute()

        val viewModel: RecipeDetailsViewModel = koinNavViewModel {
            parametersOf(recipeDetails.recipeId, onRecipeDeleted)
        }

        val state by viewModel.state.collectAsState()

        RecipeDetailsScreen(
            state = state,
            onDeleteClicked = viewModel::deleteRecipe,
        )
    }
}

fun NavController.navigateToRecipeDetails(recipeId: String) {
    navigate(RecipeDetails(recipeId = recipeId))
}