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
    viewModel {
        WelcomeViewModel(
            loginUseCase = get(),
            navHostController = get(),
        )
    }
    viewModel {
        HomeViewModel(
            getMyProductsUseCase = get(),
            addProductUseCase = get(),
            deleteProductUseCase = get(),
            navHostController = get(),
        )
    }

    viewModel { (barcode: String) ->
        AddProductViewModel(
            barcode = barcode,
            addProductManuallyUseCase = get(),
        )
    }

    viewModel {
        RecipesViewModel(
            getAllRecipesUseCase = get(),
        )
    }

    viewModel {
        val creationScope = getKoin().getOrCreateScope<CreateRecipeViewModel>("")

        AddRecipeStepViewModel(
            createRecipeUseCase = get(),
            recipeCreationRequest = creationScope.get(),
            navHostController = get(),
        )
    }

    viewModel {
        val creationScope = getKoin().getOrCreateScope<CreateRecipeViewModel>("")

        CreateRecipeViewModel(
            createRecipeUseCase = get(),
            recipeCreationRequest = creationScope.get(),
            onCleared = { creationScope.close() },
            navHostController = get(),
        )
    }

    viewModel { (recipeIdArg: String) ->
        RecipeDescriptionViewModel(
            recipeIdArg = recipeIdArg,
            getRecipeDescriptionUseCase = get(),
            deleteRecipeUseCase = get(),
            navHostController = get(),
        )
    }
}