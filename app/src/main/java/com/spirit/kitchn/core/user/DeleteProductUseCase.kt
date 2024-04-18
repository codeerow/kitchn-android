package com.spirit.kitchn.core.user

import com.spirit.kitchn.core.user.datasource.ProductDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.delete

class DeleteProductUseCase(
    private val dataSource: ProductDataSource,
    private val httpClient: HttpClient,
) {

    suspend fun execute(productId: String) {
        val response = httpClient.delete("/user/products/$productId")
        return when (response.status) {
            else -> {
                dataSource.refresh()
            }
        }
    }
}