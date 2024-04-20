package com.spirit.kitchn.ui.screen.add_product

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.AddImageArea
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.PhotoItem
import com.spirit.kitchn.ui.component.PhotosGrid
import com.spirit.kitchn.ui.theme.KTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    onDeleteAsset: (String) -> Unit,
    onAddAsset: (Uri) -> Unit,
    onAddProductClicked: () -> Unit,
    name: String,
    onNameChanged: (String) -> Unit,
    photos: List<PhotoItem.Photo>,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Product not found") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AddAssetSection(
                onAddAsset = onAddAsset,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                photos = photos,
                selectedIds = rememberSaveable { mutableStateOf(emptySet()) },
            )
            Text(
                text = "Add some photo to describe your product",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
            )
            NameField(value = name, onValueChanged = onNameChanged)
            Spacer(modifier = Modifier.height(48.dp))
            KButton(
                onClick = onAddProductClicked,
                modifier = Modifier
                    .testTag("buttonAddProduct")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Add Product",
            )
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Composable
private fun AddAssetSection(
    onAddAsset: (Uri) -> Unit,
    photos: List<PhotoItem>,
    selectedIds: MutableState<Set<Int>>,
    modifier: Modifier = Modifier,
) {
    if (photos.isEmpty()) {
        AddImageArea(
            modifier = modifier,
            directory = File(LocalContext.current.cacheDir, "images"),
            onSetUri = onAddAsset,
        )
    } else {
        PhotosGrid(
            modifier = modifier,
            photos = photos,
            selectedIds = selectedIds,
            onSetUri = onAddAsset,
        )
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
            Text("Name")
        }
    )
}

@Preview
@Composable
private fun AddProductScreenPreview() {
    KTheme {
        AddProductScreen(
            name = "",
            onNameChanged = {},
            onAddProductClicked = {},
            onAddAsset = {},
            onDeleteAsset = {},
            photos = listOf(),
        )
    }
}
