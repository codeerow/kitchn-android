package com.spirit.kitchn.ui.screen.recipe_creation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.addStepRecipeScreen
import com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step.navigateToAddRecipeStepScreen
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.CreateRecipe
import com.spirit.kitchn.ui.screen.recipe_creation.create_recipe.createRecipeScreen
import kotlinx.serialization.Serializable

@Serializable
object CreateRecipeGraph

fun NavGraphBuilder.createRecipeGraph(
    controller: NavController,
    onCreated: () -> Unit,
) {
    navigation<CreateRecipeGraph>(
        startDestination = CreateRecipe,
    ) {
        createRecipeScreen(
            onRecipeCreated = onCreated,
            onAddRecipeStep = controller::navigateToAddRecipeStepScreen,
        )
        addStepRecipeScreen(
            onAddRecipeStep = controller::navigateToAddRecipeStepScreen,
            onRecipeCreated = onCreated,
        )
    }
}