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
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.addStepRecipeScreen
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.navigateToAddRecipeStepScreen
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.createRecipeScreen
import com.spirit.kitchn.ui.screen.recipe_description.recipeDescriptionScreen
import com.spirit.kitchn.ui.screen.recipes.RECIPES_ROUTE
import com.spirit.kitchn.ui.screen.welcome.WELCOME_ROUTE
import com.spirit.kitchn.ui.screen.welcome.welcomeScreen
import org.koin.compose.koinInject


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun NavigationGraph() {
    val controller = rememberNavController()
    val tokenRepo = koinInject<TokenRepo>()

    val startDestination = if (tokenRepo.accessToken.isBlank()) WELCOME_ROUTE else NAVIGATION_BAR_HOST_ROUTE

    NavHost(
        navController = controller,
        startDestination = startDestination,
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
            onProductAdded = { controller.popBackStack(RECIPES_ROUTE, inclusive = false) },
        )
        errorScreen(
            onBackClick = controller::navigateUp,
        )
        createRecipeScreen(
            onRecipeCreated = { controller.popBackStack(route = RECIPES_ROUTE, inclusive = false) },
            onAddRecipeStep = controller::navigateToAddRecipeStepScreen,
        )
        addStepRecipeScreen(
            onAddRecipeStep = controller::navigateToAddRecipeStepScreen,
            onRecipeCreated = { controller.popBackStack(route = RECIPES_ROUTE, inclusive = false) },
        )
        recipeDescriptionScreen(
            onRecipeDeleted = { controller.popBackStack(route = RECIPES_ROUTE, inclusive = false) },
        )
    }
}
