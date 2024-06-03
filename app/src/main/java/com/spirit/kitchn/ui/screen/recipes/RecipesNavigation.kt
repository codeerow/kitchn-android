package com.spirit.kitchn.ui.screen.recipes

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.navigation.koinNavViewModel

@Serializable
object Recipes

fun NavGraphBuilder.recipesScreen(
    onAddRecipeClicked: () -> Unit,
    onRecipeItemClicked: (String) -> Unit,
) {
    composable<Recipes> {
        val viewModel: RecipesViewModel = koinNavViewModel()
        val recipes by viewModel.recipes.collectAsState()

        RecipesScreen(
            recipes = recipes,
            onAddRecipeClicked = onAddRecipeClicked,
            onItemClicked = onRecipeItemClicked,
        )
    }
}