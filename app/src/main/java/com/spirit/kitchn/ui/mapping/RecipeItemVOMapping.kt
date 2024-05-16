package com.spirit.kitchn.ui.mapping

import com.spirit.kitchn.core.recipe.model.RecipeDTO
import com.spirit.kitchn.ui.component.item.recipe.RecipeItemVO

fun RecipeDTO.toVO(): RecipeItemVO {
    return RecipeItemVO(
        id = id,
        name = name,
        imageUrl = images.getOrNull(0)?.url,
    )
}