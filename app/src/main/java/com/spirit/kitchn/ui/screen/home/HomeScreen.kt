package com.spirit.kitchn.ui.screen.home

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
import com.spirit.kitchn.core.user.product.model.Product
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.ProductItem
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddProductClicked: () -> Unit,
    onItemClicked: (String) -> Unit,
    products: List<Product>,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Kitchn") })
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
                modifier = Modifier.weight(1f)
            ) {
                items(products) {
                    ProductItem(
                        product = it,
                        onItemClick = onItemClicked
                    )
                }
            }

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

@Preview
@Composable
private fun HomeScreenPreview() {
    KTheme {
        HomeScreen(
            products = listOf(
                Product(
                    barcode = "123",
                    name = "321",
                    imageUrl = "42",
                    id = "",
                ),
                Product(
                    barcode = "das",
                    name = "fds",
                    imageUrl = "eq",
                    id = "",
                )
            ),
            onAddProductClicked = {},
            onItemClicked = {},
        )
    }
}
