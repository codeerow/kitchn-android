package com.spirit.kitchn.core.recipe.datasource

import com.spirit.kitchn.core.recipe.model.RecipeDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RecipeDataSource(
    private val httpClient: HttpClient,
    coroutineScope: CoroutineScope,
) {

    init {
        coroutineScope.launch {
            refresh()
        }
    }

    private val recipes = MutableStateFlow<List<RecipeDTO>>(listOf())

    fun observe(): Flow<List<RecipeDTO>> {
        return recipes
    }

    suspend fun refresh() {
        val response = httpClient.get("/recipe")
        recipes.emit(
            if (response.status == HttpStatusCode.NotFound) listOf()
            else response.body()
        )
    }
}