package com.spirit.kitchn.ui.screen.add_product

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
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
            NameField(value = name, onValueChanged = onNameChanged)
            Spacer(modifier = Modifier.height(48.dp))

            KButton(
                onClick = onAddProductClicked,
                modifier = Modifier
                    .testTag("buttonAddProduct")
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                label = "Add Product"
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
        )
    }
}
