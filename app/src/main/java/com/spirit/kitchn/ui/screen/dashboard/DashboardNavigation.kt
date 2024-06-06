package com.spirit.kitchn.ui.screen.dashboard

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.navigation.koinNavViewModel


@Serializable
object Dashboard

fun NavGraphBuilder.dashboardScreen() {
    composable<Dashboard> {
        val viewModel: DashboardViewModel = koinNavViewModel()
        val recipes by viewModel.recipes.collectAsState()

        DashboardScreen(
            diet = null,
            availableRecipes = recipes,
            onCreateMealPlanClicked = {},
        )
    }
}