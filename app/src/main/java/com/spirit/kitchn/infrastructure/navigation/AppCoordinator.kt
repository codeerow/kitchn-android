package com.spirit.kitchn.infrastructure.navigation

import androidx.navigation.NavController

class AppCoordinator(
    private val navController: NavController,
) {
    fun navigateHome() {
        navController.navigate(HOME_ROUTE)
    }

    fun navigateToProductCreation(barcode: String) {
        val route = PRODUCT_NOT_FOUND_ROUTE.replace(
            oldValue = "{$BARCODE_ARG}",
            newValue = barcode,
        )
        navController.navigate(route)
    }

    fun navigateToError(errorMessage: String) {
        val route = ERROR_ROUTE.replace(
            oldValue = "{$ERROR_DESCRIPTION_ARG}",
            newValue = errorMessage,
        )
        navController.navigate(route)
    }

    fun navigateToRecipeCreation() {
        navController.navigate(CREATE_RECIPE_ROUTE)
    }

    fun navigateToRecipeDescription(id: String) {
        val route = RECIPE_DESCRIPTION_ROUTE.replace("{$RECIPE_ID_ARG}", id)
        navController.navigate(route)
    }

    fun navigateToAllRecipes() {
        navController.navigate(RECIPES_ROUTE)
    }
}