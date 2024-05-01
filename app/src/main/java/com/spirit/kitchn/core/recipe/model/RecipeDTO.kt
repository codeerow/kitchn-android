package com.spirit.kitchn.core.recipe.model

data class RecipeDTO(
    val id: String,
    val title: String,
    val description: String,
) {
    val imageUrl: String = "http://localhost:3000/static/${id}_0.jpg"
}