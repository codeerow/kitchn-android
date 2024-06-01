package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.ui.screen.add_product.AddProductViewModel
import com.spirit.kitchn.ui.screen.home.HomeViewModel
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.AddRecipeStepViewModel
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.CreateRecipeViewModel
import com.spirit.kitchn.ui.screen.recipe_description.RecipeDescriptionViewModel
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
        HomeViewModel(
            getMyProductsUseCase = get(),
            addProductUseCase = get(),
            deleteProductUseCase = get(),
            navigateToProductCreation = navigateToProductCreation,
            navigateToError = navigateToError,
        )
    }

    viewModel { (barcode: String, onProductAdded: () -> Unit) ->
        AddProductViewModel(
            barcode = barcode,
            addProductManuallyUseCase = get(),
            onProductAdded = onProductAdded,
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

    viewModel { (recipeIdArg: String, onRecipeDeleted: () -> Unit) ->
        RecipeDescriptionViewModel(
            recipeIdArg = recipeIdArg,
            getRecipeDescriptionUseCase = get(),
            deleteRecipeUseCase = get(),
            onRecipeDeleted = onRecipeDeleted,
        )
    }
}