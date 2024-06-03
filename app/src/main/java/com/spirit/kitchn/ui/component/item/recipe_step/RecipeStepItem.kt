package com.spirit.kitchn.ui.component.item.recipe_step

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeStepItem(index: Int, step: StepItemVO) {
    Text(
        text = "${index.plus(1)}. ${step.description}",
        modifier = Modifier.padding(horizontal = 16.dp),
    )
    Text(
        text = "Ingredients: ${step.ingredients}",
        modifier = Modifier.padding(horizontal = 16.dp),
    )
    Spacer(modifier = Modifier.height(16.dp))
}