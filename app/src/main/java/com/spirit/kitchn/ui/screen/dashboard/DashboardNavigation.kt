package com.spirit.kitchn.ui.screen.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable


@Serializable
object Dashboard

fun NavGraphBuilder.dashboardScreen() {
    composable<Dashboard> {
        DashboardScreen(
            diet = null,
        )
    }
}