package com.spirit.kitchn.infrastructure.di.core

import com.spirit.kitchn.core.recipe.CreateRecipeUseCase
import com.spirit.kitchn.core.recipe.DeleteRecipeUseCase
import com.spirit.kitchn.core.recipe.GetAllRecipesUseCase
import com.spirit.kitchn.core.recipe.GetRecipeDescriptionUseCase
import com.spirit.kitchn.core.recipe.datasource.RecipeDataSource
import com.spirit.kitchn.ui.screen.recipe_creation.RecipeCreationCoordinator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback
import org.koin.dsl.module

val recipeModule = module {
    factory {
        GetAllRecipesUseCase(
            dataSource = get(),
        )
    }

    factory {
        GetRecipeDescriptionUseCase(
            httpClient = get(),
        )
    }

    factory {
        CreateRecipeUseCase(
            dataSource = get(),
            httpClient = get(),
            context = androidApplication(),
        )
    }

    factory {
        DeleteRecipeUseCase(
            dataSource = get(),
            httpClient = get(),
        )
    }

    single {
        val coroutineScope = CoroutineScope(Job())
        registerCallback(object : ScopeCallback {
            override fun onScopeClose(scope: Scope) {
                coroutineScope.cancel()
            }
        })

        RecipeDataSource(
            httpClient = get(),
            coroutineScope = coroutineScope,
        )
    }


    scope<RecipeCreationCoordinator> {
        scoped {
            RecipeCreationCoordinator(
                navController = get(),
                createRecipeUseCase = get()
            )
        }
        scoped { MutableStateFlow(CreateRecipeUseCase.Request()) }
    }
}