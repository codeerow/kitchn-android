package com.spirit.kitchn.infrastructure.navigation.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spirit.kitchn.infrastructure.navigation.screenFadeIn
import com.spirit.kitchn.infrastructure.navigation.screenFadeOut
import com.spirit.kitchn.infrastructure.navigation.screenSlideIn
import com.spirit.kitchn.infrastructure.navigation.screenSlideOut
import com.spirit.kitchn.ui.screen.home.HomeScreen
import com.spirit.kitchn.ui.screen.home.HomeViewModel
import com.spirit.kitchn.ui.screen.welcome.WelcomeScreen
import com.spirit.kitchn.ui.screen.welcome.WelcomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.navigation.koinNavViewModel

/* ROUTES */
internal const val WELCOME_ROUTE = "WELCOME_ROUTE"
internal const val HOME_ROUTE = "HOME_ROUTE"


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun RootHost() {
    val rootController = rememberNavController()

    NavHost(
        navController = rootController,
        startDestination = WELCOME_ROUTE,
        enterTransition = { screenSlideIn() },
        exitTransition = { screenFadeOut() },
        popEnterTransition = { screenFadeIn() },
        popExitTransition = { screenSlideOut() },
        modifier = Modifier.semantics { testTagsAsResourceId = true }
    ) {

        composable(
            route = WELCOME_ROUTE,
        ) {
            val viewModel: WelcomeViewModel = koinNavViewModel()
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()

            LaunchedEffect(key1 = Unit) {
                viewModel.onNavigateNext
                    .onEach { rootController.navigate(HOME_ROUTE) }
                    .collect()
            }

            WelcomeScreen(
                email = email,
                password = password,
                onEmailChanged = viewModel.email::tryEmit,
                onPasswordChanged = viewModel.password::tryEmit,
                onLoginClicked = viewModel::onLoginClicked,
            )
        }

        composable(
            route = HOME_ROUTE,
        ) {
            val viewModel: HomeViewModel = koinNavViewModel()
            val products by viewModel.products.collectAsState()
            val title by viewModel.title.collectAsState()

            HomeScreen(
                title = title,
                products = products,
            )
        }
    }
}