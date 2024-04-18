package com.spirit.kitchn.core.user.product.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val barcode: String,
    val name: String,
    val imageUrl: String,
)