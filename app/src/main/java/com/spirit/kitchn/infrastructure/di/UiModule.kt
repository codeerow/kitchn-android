package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.ui.screen.add_product.AddProductViewModel
import com.spirit.kitchn.ui.screen.home.HomeViewModel
import com.spirit.kitchn.ui.screen.recipe_creation.RecipeCreationCoordinator
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
            coordinator = get(),
        )
    }
    viewModel {
        HomeViewModel(
            getMyProductsUseCase = get(),
            addProductUseCase = get(),
            deleteProductUseCase = get(),
            coordinator = get(),
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
            coordinator = get(),
        )
    }

    viewModel { (curStepNumber: Int) ->
        val creationScope = getKoin().getOrCreateScope<RecipeCreationCoordinator>("")

        AddRecipeStepViewModel(
            coordinator = creationScope.get(),
            curStepNumber = curStepNumber,
        )
    }

    viewModel {
        val creationScope = getKoin().getOrCreateScope<RecipeCreationCoordinator>("")

        CreateRecipeViewModel(
            request = creationScope.get(),
            onCleared = { creationScope.close() },
            coordinator = creationScope.get(),
        )
    }

    viewModel { (recipeIdArg: String) ->
        RecipeDescriptionViewModel(
            recipeIdArg = recipeIdArg,
            getRecipeDescriptionUseCase = get(),
            deleteRecipeUseCase = get(),
            coordinator = get(),
        )
    }
}