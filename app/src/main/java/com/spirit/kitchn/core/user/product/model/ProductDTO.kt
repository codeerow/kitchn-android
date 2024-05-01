package com.spirit.kitchn.core.user.product.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: String,
    val barcode: String,
    val name: String,
) {
    val imageUrl: String = "http://localhost:3000/static/${barcode}_0.jpg"
}