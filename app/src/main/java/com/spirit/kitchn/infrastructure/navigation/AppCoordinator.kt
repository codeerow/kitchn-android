package com.spirit.kitchn.infrastructure.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppCoordinator(
    private val navController: NavController,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun navigateHome() {
        navController.navigateOnMainThread(HOME_ROUTE)
    }

    fun navigateToProductCreation(barcode: String) {
        val route = PRODUCT_NOT_FOUND_ROUTE.replace(
            oldValue = "{$BARCODE_ARG}",
            newValue = barcode,
        )
        navController.navigateOnMainThread(route)
    }

    fun navigateToError(errorMessage: String) {
        val route = ERROR_ROUTE.replace(
            oldValue = "{$ERROR_DESCRIPTION_ARG}",
            newValue = errorMessage,
        )
        navController.navigateOnMainThread(route)
    }

    fun navigateToRecipeCreation() {
        navController.navigateOnMainThread(CREATE_RECIPE_ROUTE)
    }

    fun navigateToRecipeDescription(id: String) {
        val route = RECIPE_DESCRIPTION_ROUTE.replace("{$RECIPE_ID_ARG}", id)
        navController.navigateOnMainThread(route)
    }

    fun navigateToAllRecipes() {
        navController.navigateOnMainThread(RECIPES_ROUTE)
    }

    fun navigateBackToRecipes() {
        navController.popBackStackOnMainThread(
            route = RECIPES_ROUTE,
            inclusive = false,
        )
    }

    fun navigateBackward() {
        navController.popBackStackOnMainThread()
    }

    fun navigateToNextStepCreation(curStepNumber: Int) {
        val nextStep = curStepNumber + 1
        val route = ADD_STEP_RECIPE_ROUTE.replace("{$CURRENT_STEP_NUMBER_ARG}", nextStep.toString())
        navController.navigateOnMainThread(route)
    }


    private fun NavController.navigateOnMainThread(
        route: String,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        coroutineScope.launch {
            navigate(route, navOptions, navigatorExtras)
        }
    }

    private fun NavController.popBackStackOnMainThread(
        route: String,
        inclusive: Boolean,
        saveState: Boolean = false
    ) {
        coroutineScope.launch {
            popBackStack(route, inclusive, saveState)
        }
    }

    private fun NavController.popBackStackOnMainThread() {
        coroutineScope.launch {
            popBackStack()
        }
    }
}
