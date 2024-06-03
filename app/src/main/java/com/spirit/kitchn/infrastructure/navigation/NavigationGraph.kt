package com.spirit.kitchn.infrastructure.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spirit.kitchn.core.auth.repo.TokenRepo
import com.spirit.kitchn.ui.screen.add_product.addProductScreen
import com.spirit.kitchn.ui.screen.error.errorScreen
import com.spirit.kitchn.ui.screen.recipe_creation.createRecipeGraph
import com.spirit.kitchn.ui.screen.recipe_details.recipeDetailsScreen
import com.spirit.kitchn.ui.screen.welcome.Welcome
import com.spirit.kitchn.ui.screen.welcome.welcomeScreen
import org.koin.compose.koinInject


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun NavigationGraph() {
    val controller = rememberNavController()
    val tokenRepo = koinInject<TokenRepo>()

    NavHost(
        navController = controller,
        startDestination = if (tokenRepo.accessToken.isBlank()) Welcome else NavigationBarHost,
        enterTransition = { screenSlideIn() },
        exitTransition = { screenFadeOut() },
        popEnterTransition = { screenFadeIn() },
        popExitTransition = { screenSlideOut() },
        modifier = Modifier.semantics { testTagsAsResourceId = true },
    ) {
        welcomeScreen(
            onLoginFinished = controller::navigateToAuthorizedZone,
        )
        navigationBarHost(
            rootController = controller,
        )
        addProductScreen(
            onProductAdded = { controller.popBackStack(NavigationBarHost, inclusive = false) },
        )
        errorScreen(
            onBackClicked = controller::navigateUp,
        )
        recipeDetailsScreen(
            onRecipeDeleted = { controller.popBackStack(route = NavigationBarHost, inclusive = false) },
        )
        createRecipeGraph(
            controller = controller,
            onRecipeCreated = { controller.popBackStack(route = NavigationBarHost, inclusive = false) },
        )
    }
}
