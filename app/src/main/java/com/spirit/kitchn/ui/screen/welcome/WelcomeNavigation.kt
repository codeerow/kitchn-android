package com.spirit.kitchn.ui.screen.welcome

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object Welcome

fun NavGraphBuilder.welcomeScreen(onLoginFinished: () -> Unit) {
    composable<Welcome> {
        val viewModel: WelcomeViewModel = koinNavViewModel { parametersOf(onLoginFinished) }
        val email by viewModel.email.collectAsState()
        val password by viewModel.password.collectAsState()

        WelcomeScreen(
            email = email,
            password = password,
            onEmailChanged = viewModel.email::tryEmit,
            onPasswordChanged = viewModel.password::tryEmit,
            onLoginClicked = viewModel::onLoginClicked,
        )
    }
}