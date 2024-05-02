package com.spirit.kitchn.core.user.product.usecases

import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.core.user.product.datasource.UserProductDataSource
import kotlinx.coroutines.flow.Flow

class GetMyProductsUseCase(
    private val dataSource: UserProductDataSource,
) {
    fun execute(): Flow<List<ProductDTO>> {
        return dataSource.observe()
    }
}