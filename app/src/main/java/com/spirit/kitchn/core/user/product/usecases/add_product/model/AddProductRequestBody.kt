package com.spirit.kitchn.core.user.product.usecases.add_product.model

import kotlinx.serialization.Serializable

@Serializable
data class AddProductRequestBody(val barcode: String)