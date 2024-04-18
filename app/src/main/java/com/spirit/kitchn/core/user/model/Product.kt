package com.spirit.kitchn.core.user.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val barcode: String,
    val name: String,
    val imageUrl: String,
)