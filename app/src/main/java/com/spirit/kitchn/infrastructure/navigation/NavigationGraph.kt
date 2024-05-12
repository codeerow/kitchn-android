package com.spirit.kitchn.infrastructure.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spirit.kitchn.core.user.product.usecases.add_product.AddProductUseCase
import com.spirit.kitchn.ui.screen.add_product.AddProductScreen
import com.spirit.kitchn.ui.screen.add_product.AddProductViewModel
import com.spirit.kitchn.ui.screen.error.ErrorScreen
import com.spirit.kitchn.ui.screen.home.HomeScreen
import com.spirit.kitchn.ui.screen.home.HomeViewModel
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.AddRecipeStepScreen
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.AddRecipeStepViewModel
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.CreateRecipeScreen
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.CreateRecipeViewModel
import com.spirit.kitchn.ui.screen.recipes.RecipesScreen
import com.spirit.kitchn.ui.screen.recipes.RecipesViewModel
import com.spirit.kitchn.ui.screen.welcome.WelcomeScreen
import com.spirit.kitchn.ui.screen.welcome.WelcomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.parameter.parametersOf

/* ROUTES */
internal const val WELCOME_ROUTE = "WELCOME_ROUTE"
internal const val HOME_ROUTE = "HOME_ROUTE"
internal const val RECIPES_ROUTE = "RECIPES_ROUTE"
internal const val CREATE_RECIPE_ROUTE = "ADD_RECIPE_ROUTE"
internal const val ADD_STEP_RECIPE_ROUTE = "ADD_STEP_RECIPE_ROUTE"

internal const val SCAN_ERROR_ROUTE = "SCAN_ERROR_ROUTE"
internal const val BARCODE_ARG = "barcode"
internal const val PRODUCT_NOT_FOUND_ROUTE = "PRODUCT_NOT_FOUND_ROUTE/{$BARCODE_ARG}"


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun NavigationGraph() {
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
            route = PRODUCT_NOT_FOUND_ROUTE,
            arguments = listOf(navArgument(BARCODE_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val barcodeArg = backStackEntry.arguments?.getString(BARCODE_ARG) ?: ""
            val viewModel: AddProductViewModel = koinNavViewModel { parametersOf(barcodeArg) }
            val name by viewModel.name.collectAsState()
            val productFamily by viewModel.productFamily.collectAsState()
            val photos by viewModel.photos.collectAsState()

            AddProductScreen(
                name = name,
                photos = photos,
                productFamily = productFamily,
                onAddAsset = viewModel::addAsset,
                onDeleteAsset = viewModel::deleteAsset,
                onNameChanged = viewModel.name::tryEmit,
                onAddProductClicked = viewModel::onAddProductClicked,
                onProductFamilyChanged = viewModel.productFamily::tryEmit,
            )
        }

        composable(
            route = SCAN_ERROR_ROUTE,
        ) {
            ErrorScreen(
                title = "Error during scanning",
                onBackClick = { rootController.navigateUp() },
            )
        }

        composable(
            route = HOME_ROUTE,
        ) {
            val viewModel: HomeViewModel = koinNavViewModel()
            val products by viewModel.products.collectAsState()

            LaunchedEffect(key1 = Unit) {
                viewModel.navigation
                    .onEach {
                        when (it) {
                            is AddProductUseCase.Result.Failure.ProductNotFound -> rootController.navigate(
                                PRODUCT_NOT_FOUND_ROUTE.replace("{$BARCODE_ARG}", it.barcode)
                            )

                            AddProductUseCase.Result.Failure.ScanFailed -> rootController.navigate(
                                SCAN_ERROR_ROUTE
                            )

                            AddProductUseCase.Result.Success -> {}
                        }
                    }
                    .collect()
            }

            HomeScreen(
                products = products,
                onAddProductClicked = viewModel::onAddProductClicked,
                onItemClicked = viewModel::onDeleteProductClicked,
                onShowAllRecipesClicked = { rootController.navigate(RECIPES_ROUTE) },
                onShowAvailableRecipesClicked = {},
            )
        }

        composable(
            route = RECIPES_ROUTE,
        ) {
            val viewModel: RecipesViewModel = koinNavViewModel()
            val recipes by viewModel.recipes.collectAsState()

            RecipesScreen(
                recipes = recipes,
                onAddRecipeClicked = { rootController.navigate(CREATE_RECIPE_ROUTE) },
                onItemClicked = viewModel::deleteRecipe,
            )
        }

        composable(
            route = CREATE_RECIPE_ROUTE,
        ) {
            val viewModel: CreateRecipeViewModel = koinNavViewModel()

            val name by viewModel.name.collectAsState()
            val description by viewModel.description.collectAsState()
            val preview by viewModel.preview.collectAsState()

            LaunchedEffect(key1 = Unit) {
                viewModel.navigation.onEach {
                    when (it) {
                        CreateRecipeViewModel.Navigation.AddRecipeStep -> rootController.navigate(
                            ADD_STEP_RECIPE_ROUTE
                        )

                        CreateRecipeViewModel.Navigation.RecipeCreated -> rootController.popBackStack(
                            route = RECIPES_ROUTE,
                            inclusive = false,
                        )
                    }
                }.collect()
            }

            CreateRecipeScreen(
                name = name,
                onNameChanged = viewModel.name::tryEmit,
                onCreateRecipeClicked = viewModel::createRecipe,
                onAddRecipeStepClicked = viewModel::addRecipeStep,
                onUpdateAsset = viewModel::addPreview,
                description = description,
                onDescriptionChanged = viewModel.description::tryEmit,
                photo = preview,
            )
        }

        composable(
            route = ADD_STEP_RECIPE_ROUTE,
        ) {
            val viewModel: AddRecipeStepViewModel = koinNavViewModel()

            val description by viewModel.description.collectAsState()
            val ingredients by viewModel.ingredients.collectAsState()
            val preview by viewModel.preview.collectAsState()

            LaunchedEffect(key1 = Unit) {
                viewModel.navigation.onEach {
                    when (it) {
                        AddRecipeStepViewModel.Navigation.AddRecipeStep -> rootController.navigate(
                            ADD_STEP_RECIPE_ROUTE
                        )

                        AddRecipeStepViewModel.Navigation.RecipeCreated -> rootController.popBackStack(
                            route = RECIPES_ROUTE,
                            inclusive = false,
                        )
                    }
                }.collect()
            }

            AddRecipeStepScreen(
                onCreateRecipeClicked = viewModel::createRecipe,
                onUpdateAsset = viewModel::addPreview,
                onIngredientChanged = viewModel.ingredients::tryEmit,
                ingredients = ingredients,
                description = description,
                onAddRecipeStepClicked = viewModel::addRecipeStep,
                onDescriptionChanged = viewModel.description::tryEmit,
                photo = preview,
            )
        }
    }
}