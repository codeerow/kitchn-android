package com.spirit.kitchn.ui.screen.recipe_description

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import com.spirit.kitchn.ui.theme.KTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDescriptionScreen(
    recipe: RecipeDTO?,
    onStartCookingClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(recipe?.name ?: "Loading") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            if (recipe == null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Text(
                    text = recipe.description,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Ingredients",
                )
                recipe.ingredients.forEach {
                    Text(text = it)
                }

                Spacer(modifier = Modifier.height(16.dp))


                DeleteButton(
                    onDeleteClicked = onDeleteClicked,
                )

                StartCookingButton(
                    onStartCookingClicked = onStartCookingClicked,
                )

                Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }
}

@Composable
private fun StartCookingButton(
    onStartCookingClicked: () -> Unit,
) {
    KButton(
        onClick = onStartCookingClicked,
        modifier = Modifier
            .testTag("buttonStartCooking")
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        label = "Start Cooking"
    )
}

@Composable
private fun DeleteButton(
    onDeleteClicked: () -> Unit,
) {
    KButton(
        onClick = onDeleteClicked,
        modifier = Modifier
            .testTag("buttonDelete")
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        label = "Delete"
    )
}


@Preview
@Composable
private fun RecipeDescriptionScreenPreview_loading() {
    KTheme {
        RecipeDescriptionScreen(
            recipe = null,
            onStartCookingClicked = {},
            onDeleteClicked = {}
        )
    }
}

@Preview
@Composable
private fun RecipeDescriptionScreenPreview() {
    KTheme {
        RecipeDescriptionScreen(
            recipe = RecipeDTO(
                id = "",
                name = "Name",
                description = "Description",
                images = listOf(),
                steps = listOf(
                    RecipeDTO.StepDTO(
                        id = "",
                        description = "desc",
                        ingredients = listOf("ingredient", "ingredient2"),
                        preview = null,
                    )
                )
            ),
            onStartCookingClicked = {},
            onDeleteClicked = {}
        )
    }
}