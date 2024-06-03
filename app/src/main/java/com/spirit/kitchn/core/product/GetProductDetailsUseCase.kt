package com.spirit.kitchn.core.product

import com.spirit.kitchn.core.product.model.ProductDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GetProductDetailsUseCase(
    private val httpClient: HttpClient,
) {
    suspend fun execute(id: String): ProductDTO {
        return httpClient.get("/product/${id}").body()
    }
}