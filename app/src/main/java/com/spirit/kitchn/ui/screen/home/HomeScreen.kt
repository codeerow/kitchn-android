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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.ProductItem
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddProductClicked: () -> Unit,
    onShowAllRecipesClicked: () -> Unit,
    onShowAvailableRecipesClicked: () -> Unit,
    onItemClicked: (String) -> Unit,
    products: List<ProductDTO>,
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
            if (products.isEmpty()) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    text = "You have no products",
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    items(products) {
                        ProductItem(
                            product = it,
                            onItemClick = onItemClicked,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            KButton(
                onClick = onShowAllRecipesClicked,
                modifier = Modifier
                    .testTag("seeAllRecipes")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "All Recipes"
            )
            KButton(
                onClick = onShowAvailableRecipesClicked,
                modifier = Modifier
                    .testTag("seeAvailableRecipes")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "See Available Recipes"
            )
            KButton(
                onClick = onAddProductClicked,
                modifier = Modifier
                    .testTag("buttonAddProduct")
                    .padding(horizontal = 16.dp)
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
                ProductDTO(
                    barcode = "123",
                    name = "321",
                    id = "",
                ),
                ProductDTO(
                    barcode = "das",
                    name = "fds",
                    id = "",
                )
            ),
            onAddProductClicked = {},
            onItemClicked = {},
            onShowAllRecipesClicked = {},
            onShowAvailableRecipesClicked = {},
        )
    }
}
