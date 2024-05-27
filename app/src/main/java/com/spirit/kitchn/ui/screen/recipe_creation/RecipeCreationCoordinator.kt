package com.spirit.kitchn.ui.screen.recipe_creation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.spirit.kitchn.core.recipe.CreateRecipeUseCase
import com.spirit.kitchn.infrastructure.navigation.ADD_STEP_RECIPE_ROUTE
import com.spirit.kitchn.infrastructure.navigation.CREATE_RECIPE_ROUTE
import com.spirit.kitchn.infrastructure.navigation.CURRENT_STEP_NUMBER_ARG
import com.spirit.kitchn.infrastructure.navigation.RECIPES_ROUTE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeCreationCoordinator(
    private val navController: NavController,
    private val createRecipeUseCase: CreateRecipeUseCase,
) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun start() {
        navController.navigateOnMainThread(CREATE_RECIPE_ROUTE)
    }


    fun navigateBackward() {
        navController.popBackStackOnMainThread()
    }

    fun addStep(curStepNumber: Int) {
        val nextStep = curStepNumber + 1
        val route = ADD_STEP_RECIPE_ROUTE.replace("{$CURRENT_STEP_NUMBER_ARG}", nextStep.toString())
        navController.navigateOnMainThread(route)
    }

    fun close() {
        navController.popBackStackOnMainThread(RECIPES_ROUTE, false)
    }

    fun createRecipe(request: CreateRecipeUseCase.Request) {
        coroutineScope.launch {
            createRecipeUseCase.execute(request)
            close()
        }
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