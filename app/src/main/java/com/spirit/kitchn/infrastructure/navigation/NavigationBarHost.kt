package com.spirit.kitchn.infrastructure.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spirit.kitchn.ui.component.BottomNavigationBar
import com.spirit.kitchn.ui.screen.add_product.navigateToAddProductScreen
import com.spirit.kitchn.ui.screen.cooking.Cooking
import com.spirit.kitchn.ui.screen.cooking.cookingScreen
import com.spirit.kitchn.ui.screen.error.navigateToError
import com.spirit.kitchn.ui.screen.home.Pantry
import com.spirit.kitchn.ui.screen.home.pantryScreen
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.navigateToCreateRecipe
import com.spirit.kitchn.ui.screen.recipe_details.navigateToRecipeDetails
import com.spirit.kitchn.ui.screen.recipes.Recipes
import com.spirit.kitchn.ui.screen.recipes.recipesScreen
import kotlinx.serialization.Serializable

@Serializable
object NavigationBarHost

enum class BottomNavItem(val route: Any, val icon: ImageVector, val label: String) {
    COOKING(Cooking, Icons.Default.Cookie, "Cooking"),
    PANTRY(Pantry, Icons.Default.FoodBank, "Pantry"),
    RECIPES(Recipes, Icons.Default.Fastfood, "Recipes"),
}

@OptIn(ExperimentalComposeUiApi::class)
fun NavGraphBuilder.navigationBarHost(rootController: NavController) {
    composable<NavigationBarHost> {
        val controller = rememberNavController()

        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = controller,
                startDestination = Cooking,
                enterTransition = { screenSlideIn() },
                exitTransition = { screenFadeOut() },
                popEnterTransition = { screenFadeIn() },
                popExitTransition = { screenSlideOut() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .semantics { testTagsAsResourceId = true },
            ) {

                pantryScreen(
                    onAddProductClicked = rootController::navigateToAddProductScreen,
                    onProductClicked = rootController::navigateToAddProductScreen,
                    onError = rootController::navigateToError,
                )
                recipesScreen(
                    onAddRecipeClicked = rootController::navigateToCreateRecipe,
                    onRecipeItemClicked = rootController::navigateToRecipeDetails,
                )
                cookingScreen()
            }

            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomNavigationBar(
                    navController = controller,
                )
            }
        }
    }
}


fun NavController.navigateToAuthorizedZone() {
    navigate(NavigationBarHost)
}