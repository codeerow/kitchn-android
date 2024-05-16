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
import com.spirit.kitchn.core.recipe.model.Image
import com.spirit.kitchn.core.recipe.model.RecipeDTO
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.theme.KTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDescriptionScreen(
    recipe: RecipeDTO?,
    onStartCookingClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    isPreview: Boolean = false,
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
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    HeaderImage(
                        image = recipe.preview,
                        isPreview = isPreview,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = recipe.description,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    IngredientsSection(items = recipe.ingredients)
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
private fun IngredientsSection(
    items: List<RecipeDTO.ProductFamilyDTO>,
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
                text = it.title,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun HeaderImage(
    image: Image?,
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
        image?.url?.let { preview ->
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
            recipe = RecipeDTO(
                id = "",
                name = "Name",
                description = "Description",
                images = listOf(),
                steps = listOf(
                    RecipeDTO.StepDTO(
                        id = "",
                        description = "desc",
                        productFamilies = listOf(
                            RecipeDTO.ProductFamilyDTO(
                                id = "ingredient",
                                title = "asd",
                                description = "asd",
                            ),
                            RecipeDTO.ProductFamilyDTO(
                                id = "ingredient 2",
                                title = "asd",
                                description = "asd",
                            ),
//                        preview = null,
                        ),
                    ),
                ),
            ),
            onStartCookingClicked = {},
            onDeleteClicked = {},
            isPreview = true,
        )
    }
}