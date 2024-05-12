package com.spirit.kitchn.core.recipe

import com.spirit.kitchn.core.recipe.model.RecipeDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GetRecipeDescriptionUseCase(
    private val httpClient: HttpClient,
) {
    suspend fun execute(id: String): RecipeDTO {
        return httpClient.get("/recipe/${id}").body()
    }
}