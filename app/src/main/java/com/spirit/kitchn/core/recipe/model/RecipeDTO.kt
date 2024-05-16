package com.spirit.kitchn.core.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val id: String,
    val name: String,
    val description: String,
    val images: List<Image>,
    val steps: List<StepDTO>,
) {

    @Serializable
    data class StepDTO(
        val id: String,
        val description: String,
        val productFamilies: List<ProductFamilyDTO> = listOf(), // TODO: rename to ingredients both here and on the server
//        val preview: Image?,
    )

    @Serializable
    data class ProductFamilyDTO(
        val id: String,
        val title: String,
        val description: String,
    )
}

@Serializable
data class Image(
    val name: String,
) {
    val url: String = "http://localhost:3000/static/${name}"
}