package com.spirit.kitchn.ui.screen.add_product

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.MyImageArea
import com.spirit.kitchn.ui.component.Photo
import com.spirit.kitchn.ui.component.PhotosGrid
import com.spirit.kitchn.ui.theme.KTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    onDeleteAssetClicked: (String) -> Unit,
    onAddAssetClicked: () -> Unit,
    onAddProductClicked: () -> Unit,
    name: String,
    onNameChanged: (String) -> Unit,
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
            Spacer(modifier = Modifier.weight(1f))
            AddAssetSection(
                onAddAssetClicked = onAddAssetClicked,
                modifier = Modifier
                    .height(200.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                photos = listOf(),
                selectedIds = rememberSaveable { mutableStateOf(emptySet()) },
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
    onAddAssetClicked: () -> Unit,
    photos: List<Photo>,
    selectedIds: MutableState<Set<Int>>,
    modifier: Modifier = Modifier,
) {
    if (photos.isEmpty()) {
//        Surface(
//            modifier = modifier.clickable {
//                onAddAssetClicked()
//            },
//            color = Color(74, 94, 141),
//            shape = RoundedCornerShape(24.dp),
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                tint = Color.White,
//                contentDescription = null,
//            )
//        }
        val uri = remember { mutableStateOf<Uri?>(null) }
        MyImageArea(
            directory = File(LocalContext.current.cacheDir, "images"),
            uri = uri.value,
            onSetUri = {
                uri.value = it
            },
        )
    } else {
        PhotosGrid(
            modifier = modifier,
            photos = photos,
            selectedIds = selectedIds,
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
            onAddAssetClicked = {},
            onDeleteAssetClicked = {},
        )
    }
}
