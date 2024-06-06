package com.spirit.kitchn.core.user

import com.spirit.kitchn.core.recipe.model.RecipeDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class GetAvailableRecipesUseCase(
    private val httpClient: HttpClient,
) {
    suspend fun execute(): List<RecipeDTO> {
        val response = httpClient.get("/user/recipes")
        return if (response.status == HttpStatusCode.NotFound) listOf()
        else response.body()
    }
}