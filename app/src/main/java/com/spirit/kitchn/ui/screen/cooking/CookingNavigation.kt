package com.spirit.kitchn.ui.screen.cooking

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Cooking

fun NavGraphBuilder.cookingScreen() {
    composable<Cooking> {
        CookingScreen()
    }
}
