package com.spirit.kitchn.ui.screen.recipe_description

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spirit.kitchn.R
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.screen.recipe_description.model.StepItemVO
import com.spirit.kitchn.ui.theme.KTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDescriptionScreen(
    state: RecipeDescriptionViewModel.State,
    onDeleteClicked: () -> Unit,
    isPreview: Boolean = false,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(state.title) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            when (state) {
                RecipeDescriptionViewModel.State.Loading -> Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }

                is RecipeDescriptionViewModel.State.Content -> {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        HeaderImage(
                            imageUrl = state.headerImageUrl,
                            isPreview = isPreview,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray),
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.description,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        IngredientsSection(items = state.ingredients)

                        Spacer(modifier = Modifier.height(16.dp))
                        StepsSection(items = state.steps)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    DeleteButton(
                        onDeleteClicked = onDeleteClicked,
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                }
            }
        }
    }
}


@Composable
private fun IngredientsSection(
    items: List<String>,
) {
    Text(
        text = "Ingredients",
        fontSize = 20.sp,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
    if (items.isEmpty()) {
        Text(
            text = "This recipe has no ingredients",
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    } else {
        items.forEach {
            Text(
                text = it,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun StepsSection(
    items: List<StepItemVO>,
) {
    Text(
        text = "Steps",
        fontSize = 20.sp,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
    if (items.isEmpty()) {
        Text(
            text = "This recipe has no steps",
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    } else {
        items.forEachIndexed { index, step ->
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
    }
}

@Composable
private fun HeaderImage(
    imageUrl: String?,
    isPreview: Boolean,
    modifier: Modifier,
) {
    if (isPreview) {
        Image(
            painterResource(id = R.drawable.imgone),
            contentDescription = "images",
            contentScale = ContentScale.FillWidth,
            modifier = modifier,
        )
    } else {
        imageUrl?.let { preview ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(preview)
                    .crossfade(true)
                    .build(),
                contentDescription = "images",
                contentScale = ContentScale.FillWidth,
                modifier = modifier,
            )
        }
    }
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
            state = RecipeDescriptionViewModel.State.Loading,
            onDeleteClicked = {},
            isPreview = true,
        )
    }
}

@Preview
@Composable
private fun RecipeDescriptionScreenPreview() {
    KTheme {
        RecipeDescriptionScreen(
            state = RecipeDescriptionViewModel.State.Content(
                title = "Name",
                description = "Description",
                headerImageUrl = null,
                steps = listOf(
                    StepItemVO(
                        description = "desc",
                        ingredients = "",
                    ),
                ),
                ingredients = listOf("321", "123")
            ),
            onDeleteClicked = {},
            isPreview = true,
        )
    }
}