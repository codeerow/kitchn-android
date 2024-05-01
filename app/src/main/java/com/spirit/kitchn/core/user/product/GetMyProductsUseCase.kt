package com.spirit.kitchn.core.user.product

import com.spirit.kitchn.core.user.product.datasource.ProductDataSource
import com.spirit.kitchn.core.user.product.model.ProductDTO
import kotlinx.coroutines.flow.Flow

class GetMyProductsUseCase(
    private val dataSource: ProductDataSource,
) {
    fun execute(): Flow<List<ProductDTO>> {
        return dataSource.observe()
    }
}