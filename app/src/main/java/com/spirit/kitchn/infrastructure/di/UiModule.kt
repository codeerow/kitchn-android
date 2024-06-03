package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.ui.screen.add_product.AddProductViewModel
import com.spirit.kitchn.ui.screen.pantry.PantryViewModel
import com.spirit.kitchn.ui.screen.product_details.ProductDetailsViewModel
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.AddRecipeStepViewModel
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.CreateRecipeViewModel
import com.spirit.kitchn.ui.screen.recipe_details.RecipeDetailsViewModel
import com.spirit.kitchn.ui.screen.recipes.RecipesViewModel
import com.spirit.kitchn.ui.screen.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel { (onLoginFinished: () -> Unit) ->
        WelcomeViewModel(
            loginUseCase = get(),
            onLoginFinished = onLoginFinished,
        )
    }

    viewModel { (
                    navigateToProductCreation: (String) -> Unit,
                    navigateToError: (String) -> Unit,
                ) ->
        PantryViewModel(
            getMyProductsUseCase = get(),
            addProductUseCase = get(),
            deleteProductUseCase = get(),
            navigateToProductCreation = navigateToProductCreation,
            navigateToError = navigateToError,
        )
    }

    viewModel { (barcode: String, onAdded: () -> Unit) ->
        AddProductViewModel(
            barcode = barcode,
            addProductManuallyUseCase = get(),
            onAdded = onAdded,
        )
    }

    viewModel {
        RecipesViewModel(
            getAllRecipesUseCase = get(),
        )
    }

    viewModel { (onAddRecipeStep: () -> Unit, onRecipeCreated: () -> Unit) ->
        val creationScope = getKoin().getOrCreateScope<CreateRecipeViewModel>("")

        AddRecipeStepViewModel(
            createRecipeUseCase = get(),
            recipeCreationRequest = creationScope.get(),
            onAddRecipeStep = onAddRecipeStep,
            onRecipeCreated = onRecipeCreated,
        )
    }

    viewModel { (onRecipeCreated: () -> Unit, onAddRecipeStep: () -> Unit) ->
        val creationScope = getKoin().getOrCreateScope<CreateRecipeViewModel>("")

        CreateRecipeViewModel(
            createRecipeUseCase = get(),
            recipeCreationRequest = creationScope.get(),
            onCleared = { creationScope.close() },
            onRecipeCreated = onRecipeCreated,
            onAddRecipeStep = onAddRecipeStep,
        )
    }

    viewModel { (recipeIdArg: String, onDeleted: () -> Unit) ->
        RecipeDetailsViewModel(
            recipeIdArg = recipeIdArg,
            getRecipeDetailsUseCase = get(),
            deleteRecipeUseCase = get(),
            onDeleted = onDeleted,
        )
    }

    viewModel { (productIdArg: String) ->
        ProductDetailsViewModel(
            productIdArg = productIdArg,
            getProductDetailsUseCase = get(),
        )
    }
}