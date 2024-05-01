package com.spirit.kitchn.infrastructure.di

import com.spirit.kitchn.core.recipe.GetAllRecipesUseCase
import com.spirit.kitchn.core.recipe.datasource.RecipeDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback
import org.koin.dsl.module

val recipeModule = module {
    factory {
        GetAllRecipesUseCase(
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
            coroutineScope = coroutineScope
        )
    }
}