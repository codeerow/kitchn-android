package com.spirit.kitchn.ui.screen.error

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ERROR_DESCRIPTION_ARG = "ERROR_DESCRIPTION_ARG"
const val ERROR_ROUTE = "ERROR_ROUTE/{$ERROR_DESCRIPTION_ARG}"

fun NavGraphBuilder.errorScreen(onBackClick: () -> Unit) {
    composable(
        route = ERROR_ROUTE,
        arguments = listOf(navArgument(ERROR_DESCRIPTION_ARG) { type = NavType.StringType })
    ) { backStackEntry ->
        val errorDescriptionArg = backStackEntry.arguments?.getString(ERROR_DESCRIPTION_ARG) ?: ""
        ErrorScreen(
            title = errorDescriptionArg,
            onBackClick = onBackClick,
        )
    }
}

fun NavController.navigateToError(errorMessage: String) {
    val route = ERROR_ROUTE.replace(
        oldValue = "{$ERROR_DESCRIPTION_ARG}",
        newValue = errorMessage,
    )
    navigate(route)
}