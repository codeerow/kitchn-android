package com.spirit.kitchn.ui.screen.recipe_description

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

const val RECIPE_ID_ARG = "RECIPE_ID_ARG"
const val RECIPE_DESCRIPTION_ROUTE = "RECIPE_DESCRIPTION_ROUTE/{$RECIPE_ID_ARG}"

fun NavGraphBuilder.recipeDescriptionScreen(
    onRecipeDeleted: () -> Unit,
) {
    composable(
        route = RECIPE_DESCRIPTION_ROUTE,
        arguments = listOf(navArgument(RECIPE_ID_ARG) { type = NavType.StringType })
    ) { backStackEntry ->
        val recipeIdArg = backStackEntry.arguments?.getString(RECIPE_ID_ARG) ?: ""

        val viewModel: RecipeDescriptionViewModel = koinNavViewModel {
            parametersOf(recipeIdArg, onRecipeDeleted)
        }

        val state by viewModel.state.collectAsState()

        RecipeDescriptionScreen(
            state = state,
            onDeleteClicked = viewModel::deleteRecipe,
        )
    }
}

fun NavController.navigateToRecipeDescription(recipeId: String) {
    val route = RECIPE_DESCRIPTION_ROUTE.replace("{$RECIPE_ID_ARG}", recipeId)
    navigate(route)
}