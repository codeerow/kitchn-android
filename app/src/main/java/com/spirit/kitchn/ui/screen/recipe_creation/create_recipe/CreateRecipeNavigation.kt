package com.spirit.kitchn.ui.screen.recipe_creation.create_recipe

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

const val CREATE_RECIPE_ROUTE = "ADD_RECIPE_ROUTE"

fun NavGraphBuilder.createRecipeScreen(
    onRecipeCreated: () -> Unit,
    onAddRecipeStep: () -> Unit,
) {
    composable(
        route = CREATE_RECIPE_ROUTE,
    ) {
        val viewModel: CreateRecipeViewModel =
            koinNavViewModel { parametersOf(onRecipeCreated, onAddRecipeStep) }

        val name by viewModel.name.collectAsState()
        val preview by viewModel.preview.collectAsState()
        val description by viewModel.description.collectAsState()

        CreateRecipeScreen(
            name = name,
            photo = preview,
            description = description,
            onUpdateAsset = viewModel::addPreview,
            onNameChanged = viewModel.name::tryEmit,
            onCreateRecipeClicked = viewModel::createRecipe,
            onAddRecipeStepClicked = viewModel::addRecipeStep,
            onDescriptionChanged = viewModel.description::tryEmit,
        )
    }
}

fun NavController.navigateToCreateRecipe() {
    navigate(CREATE_RECIPE_ROUTE)
}