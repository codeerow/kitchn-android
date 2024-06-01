package com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

const val ADD_STEP_RECIPE_ROUTE = "ADD_STEP_RECIPE_ROUTE"

fun NavGraphBuilder.addStepRecipeScreen(
    onAddRecipeStep: () -> Unit,
    onRecipeCreated: () -> Unit,
) {
    composable(
        route = ADD_STEP_RECIPE_ROUTE,
    ) {
        val viewModel: AddRecipeStepViewModel =
            koinNavViewModel { parametersOf(onAddRecipeStep, onRecipeCreated) }

        val preview by viewModel.preview.collectAsState()
        val description by viewModel.description.collectAsState()
        val ingredients by viewModel.ingredients.collectAsState()

        AddRecipeStepScreen(
            photo = preview,
            ingredients = ingredients,
            description = description,
            onUpdateAsset = viewModel::addPreview,
            onCreateRecipeClicked = viewModel::createRecipe,
            onAddRecipeStepClicked = viewModel::addRecipeStep,
            onIngredientChanged = viewModel.ingredients::tryEmit,
            onDescriptionChanged = viewModel.description::tryEmit,
        )
    }
}


fun NavController.navigateToAddRecipeStepScreen() {
    navigate(ADD_STEP_RECIPE_ROUTE)
}