package com.spirit.kitchn.ui.mapping

import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.ui.component.item.product.ProductItemVO

fun ProductDTO.toVO(): ProductItemVO {
    return ProductItemVO(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
}