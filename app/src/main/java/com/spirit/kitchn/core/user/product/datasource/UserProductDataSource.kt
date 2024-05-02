package com.spirit.kitchn.core.user.product.datasource

import com.spirit.kitchn.core.product.model.ProductDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserProductDataSource(
    private val httpClient: HttpClient,
    coroutineScope: CoroutineScope,
) {

    init {
        coroutineScope.launch {
            refresh()
        }
    }

    private val products = MutableStateFlow<List<ProductDTO>>(listOf())

    fun observe(): Flow<List<ProductDTO>> {
        return products
    }

    suspend fun refresh() {
        val response = httpClient.get("/user/products")
        products.emit(
            if (response.status == HttpStatusCode.NotFound) listOf()
            else response.body()
        )
    }
}