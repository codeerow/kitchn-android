package com.spirit.kitchn.core.user

import com.spirit.kitchn.core.user.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class GetMyProductsUseCase(
    private val httpClient: HttpClient,
) {
    suspend fun execute(): List<Product> {
        val response = httpClient.get("/user/products")
        return if (response.status == HttpStatusCode.NotFound) listOf()
        else response.body()
    }
}