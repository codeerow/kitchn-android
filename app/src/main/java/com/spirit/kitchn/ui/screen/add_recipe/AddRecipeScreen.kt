package com.spirit.kitchn.ui.screen.add_recipe

import android.net.Uri
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.AddImageArea
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.PhotoItem
import com.spirit.kitchn.ui.theme.KTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    onDeleteAsset: (Int) -> Unit,
    onAddAsset: (Uri) -> Unit,
    onAddRecipeClicked: () -> Unit,
    name: String,
    description: String,
    productFamily: String,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onProductFamilyChanged: (String) -> Unit,
    photo: PhotoItem.Photo?,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Create your recipe") })
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
                directory = File(LocalContext.current.cacheDir, "recipes"),
                onSetUri = onAddAsset,
            )
            Text(
                text = "Add preview for your recipe",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
            )
            NameField(value = name, onValueChanged = onNameChanged)
            Spacer(modifier = Modifier.height(16.dp))

            DescriptionField(value = description, onValueChanged = onDescriptionChanged)
            Spacer(modifier = Modifier.height(16.dp))

            ProductFamilyField(value = productFamily, onValueChanged = onProductFamilyChanged)
            Spacer(modifier = Modifier.height(16.dp))


            KButton(
                onClick = onAddRecipeClicked,
                modifier = Modifier
                    .testTag("buttonAddStep")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Add Step",
            )
            Spacer(modifier = Modifier.height(48.dp))

            KButton(
                onClick = onAddRecipeClicked,
                modifier = Modifier
                    .testTag("buttonAddRecipe")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Add Recipe",
            )
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}


@Composable
private fun NameField(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text("Title")
        }
    )
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
private fun ProductFamilyField(value: String, onValueChanged: (String) -> Unit) {
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
private fun AddRecipeScreenScreenPreview() {
    KTheme {
        AddRecipeScreen(
            name = "",
            onNameChanged = {},
            onAddRecipeClicked = {},
            onAddAsset = {},
            onDeleteAsset = {},
            onProductFamilyChanged = {},
            productFamily = "",
            description = "",
            onDescriptionChanged = {},
            photo = PhotoItem.Photo(1, ""),
        )
    }
}

// TODO: add title, description, preview
//       add step: {
//          title
//          ingredients
//          description
//          image
//       }
//

@Preview
@Composable
private fun AddRecipeScreenPreview_withoutPhoto() {
    KTheme {
        AddRecipeScreen(
            name = "",
            onNameChanged = {},
            onAddRecipeClicked = {},
            onAddAsset = {},
            onDeleteAsset = {},
            onProductFamilyChanged = {},
            productFamily = "",
            description = "",
            onDescriptionChanged = {},
            photo = null,
        )
    }
}
