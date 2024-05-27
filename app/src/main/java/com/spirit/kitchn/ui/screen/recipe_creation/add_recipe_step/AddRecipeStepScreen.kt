package com.spirit.kitchn.ui.screen.recipe_creation.add_recipe_step

import android.net.Uri
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.AddImageArea
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.photos_grid.PhotoItem
import com.spirit.kitchn.ui.theme.KTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeStepScreen(
    onUpdateAsset: (Uri) -> Unit,
    onCreateRecipeClicked: () -> Unit,
    onAddRecipeStepClicked: () -> Unit,
    description: String,
    ingredients: String,
    onBackPressed: () -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onIngredientChanged: (String) -> Unit,
    photo: PhotoItem,
) {
    LocalLifecycleOwner.current
    BackHandler { onBackPressed() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Add step") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AddImageArea(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                directory = File(LocalContext.current.cacheDir, "images"),
                onSetUri = onUpdateAsset,
            )
            Text(
                text = "Add step image",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
            )
            IngredientsField(value = ingredients, onValueChanged = onIngredientChanged)
            Spacer(modifier = Modifier.height(16.dp))

            DescriptionField(value = description, onValueChanged = onDescriptionChanged)
            Spacer(modifier = Modifier.height(16.dp))

            KButton(
                onClick = onAddRecipeStepClicked,
                modifier = Modifier
                    .testTag("buttonAddStep")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Add Step",
            )

            KButton(
                onClick = onCreateRecipeClicked,
                modifier = Modifier
                    .testTag("buttonCreate")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Create",
            )
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Composable
private fun DescriptionField(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text("Description")
        }
    )
}

@Composable
private fun IngredientsField(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text("Ingredients")
        }
    )
}


@Preview
@Composable
private fun AddRecipeStepScreenPreview_withoutPhoto() {
    KTheme {
        AddRecipeStepScreen(
            onCreateRecipeClicked = {},
            onUpdateAsset = {},
            onIngredientChanged = {},
            ingredients = "",
            description = "",
            onAddRecipeStepClicked = {},
            onDescriptionChanged = {},
            onBackPressed = {},
            photo = PhotoItem.AddPhotoItem,
        )
    }
}
