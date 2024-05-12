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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.item.RecipeItem
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    onAddRecipeClicked: () -> Unit,
    onItemClicked: (String) -> Unit,
    recipes: List<RecipeDTO>,
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
            Column(
                modifier = Modifier
                    .weight(1f)
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


@Composable
fun CustomStaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val columnWidth = (constraints.maxWidth / numColumns)
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val columnHeights = IntArray(numColumns) { 0 }
        val placeables = measurables.map { measurable ->
            val column = testColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)
            columnHeights[column] += placeable.height
            placeable
        }

        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val columnYPointers = IntArray(numColumns) { 0 }

            placeables.forEach { placeable ->
                val column = testColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )
                columnYPointers[column] += placeable.height
            }
        }
    }
}

private fun testColumn(columnHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var columnIndex = 0
    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }
    return columnIndex
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
                    images = listOf(),
                ),
                RecipeDTO(
                    name = "das",
                    description = "fds",
                    id = "",
                    images = listOf(),
                )
            ),
            onAddRecipeClicked = {},
            onItemClicked = {},
            isPreview = true,
        )
    }
}
