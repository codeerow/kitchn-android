package com.spirit.kitchn.ui.screen.error

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class Error(val message: String)

fun NavGraphBuilder.errorScreen(onBackClicked: () -> Unit) {
    composable<Error> { backStackEntry ->
        val error: Error = backStackEntry.toRoute()
        ErrorScreen(
            title = error.message,
            onBackClick = onBackClicked,
        )
    }
}

fun NavController.navigateToError(errorMessage: String) {
    navigate(Error(message = errorMessage))
}