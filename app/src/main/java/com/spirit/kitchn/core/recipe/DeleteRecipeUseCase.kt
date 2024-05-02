package com.spirit.kitchn.core.recipe

import com.spirit.kitchn.core.recipe.datasource.RecipeDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.delete

class DeleteRecipeUseCase(
    private val dataSource: RecipeDataSource,
    private val httpClient: HttpClient,
) {

    suspend fun execute(recipeId: String) {
        val response = httpClient.delete("/recipe/$recipeId")
        return when (response.status) {
            else -> {
                dataSource.refresh()
            }
        }
    }
}