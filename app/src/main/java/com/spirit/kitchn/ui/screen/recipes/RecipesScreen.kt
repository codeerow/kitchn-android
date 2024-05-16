package com.spirit.kitchn.ui.screen.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.CustomStaggeredVerticalGrid
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.item.recipe.RecipeItem
import com.spirit.kitchn.ui.component.item.recipe.RecipeItemVO
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    onAddRecipeClicked: () -> Unit,
    onItemClicked: (String) -> Unit,
    recipes: List<RecipeItemVO>,
    isPreview: Boolean = false,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Recipes") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RecipesGrid(
                modifier = Modifier
                    .weight(1f),
                onItemClicked = onItemClicked,
                isPreview = isPreview,
                recipes = recipes,
            )
            Spacer(modifier = Modifier.height(16.dp))
            AddRecipeButton(onAddRecipeClicked = onAddRecipeClicked)

            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Composable
private fun RecipesGrid(
    modifier: Modifier = Modifier,
    recipes: List<RecipeItemVO>,
    isPreview: Boolean = false,
    onItemClicked: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(5.dp)
    ) {
        CustomStaggeredVerticalGrid(
            numColumns = 2,
            modifier = Modifier.padding(5.dp)
        ) {
            recipes.forEach { recipe ->
                RecipeItem(
                    item = recipe,
                    isPreview = isPreview,
                    onItemClicked = onItemClicked,
                )
            }
        }
    }
}

@Composable
private fun AddRecipeButton(
    onAddRecipeClicked: () -> Unit,
) {
    KButton(
        onClick = onAddRecipeClicked,
        modifier = Modifier
            .testTag("buttonAddRecipe")
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        label = "Add Recipe"
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    KTheme {
        RecipesScreen(
            recipes = listOf(
                RecipeItemVO(
                    id = "",
                    name = "123",
                    imageUrl = null,
                ),
                RecipeItemVO(
                    id = "",
                    name = "das",
                    imageUrl = null,
                )
            ),
            onAddRecipeClicked = {},
            onItemClicked = {},
            isPreview = true,
        )
    }
}
