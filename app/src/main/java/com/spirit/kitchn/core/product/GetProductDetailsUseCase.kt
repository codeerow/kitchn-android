package com.spirit.kitchn.core.product

import com.spirit.kitchn.core.product.api.ProductAPI
import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.infrastructure.network.executeWith
import io.ktor.client.HttpClient

class GetProductDetailsUseCase(
    private val httpClient: HttpClient,
) {
    suspend fun execute(id: String): ProductDTO {
        return ProductAPI.GetProductDetailsEndpoint(id = id)
            .executeWith(httpClient)
    }
}