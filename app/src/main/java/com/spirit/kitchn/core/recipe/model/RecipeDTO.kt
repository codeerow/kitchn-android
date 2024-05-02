package com.spirit.kitchn.core.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val id: String,
    val name: String,
    val description: String,
) {
    val imageUrl: String = "http://localhost:3000/static/${name}_preview.jpg"
}