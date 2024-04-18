package com.spirit.kitchn.core.user

import com.spirit.kitchn.core.user.datasource.ProductDataSource
import com.spirit.kitchn.core.user.model.Product
import kotlinx.coroutines.flow.Flow

class GetMyProductsUseCase(
    private val dataSource: ProductDataSource,
) {
    fun execute(): Flow<List<Product>> {
        return dataSource.observe()
    }
}