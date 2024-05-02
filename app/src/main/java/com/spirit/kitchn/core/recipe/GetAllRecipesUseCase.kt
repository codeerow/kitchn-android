package com.spirit.kitchn.core.recipe

import com.spirit.kitchn.core.recipe.datasource.RecipeDataSource
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import kotlinx.coroutines.flow.Flow

class GetAllRecipesUseCase(
    private val dataSource: RecipeDataSource,
) {
    fun execute(): Flow<List<RecipeDTO>> {
        return dataSource.observe()
    }
}