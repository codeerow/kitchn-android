package com.spirit.kitchn.ui.screen.recipes

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel


const val RECIPES_ROUTE = "RECIPES_ROUTE"

fun NavController.navigateToAllRecipes() {
    navigate(RECIPES_ROUTE)
}

fun NavGraphBuilder.recipesScreen(
    addRecipe: () -> Unit,
    onItemClicked: (String) -> Unit,
) {
    composable(
        route = RECIPES_ROUTE,
    ) {
        val viewModel: RecipesViewModel = koinNavViewModel()
        val recipes by viewModel.recipes.collectAsState()

        RecipesScreen(
            recipes = recipes,
            onAddRecipeClicked = addRecipe,
            onItemClicked = onItemClicked,
        )
    }
}