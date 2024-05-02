package com.spirit.kitchn.ui.screen.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.RecipeItem
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    onAddRecipeClicked: () -> Unit,
    onItemClicked: (String) -> Unit,
    recipes: List<RecipeDTO>,
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                items(recipes) {
                    RecipeItem(
                        recipe = it,
                        onItemClick = onItemClicked
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            KButton(
                onClick = onAddRecipeClicked,
                modifier = Modifier
                    .testTag("buttonAddRecipe")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Add Recipe"
            )
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    KTheme {
        RecipesScreen(
            recipes = listOf(
                RecipeDTO(
                    name = "123",
                    description = "321",
                    id = "",
                ),
                RecipeDTO(
                    name = "das",
                    description = "fds",
                    id = "",
                )
            ),
            onAddRecipeClicked = {},
            onItemClicked = {},
        )
    }
}
