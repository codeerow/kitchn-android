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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.KSwipeToDismissBox
import com.spirit.kitchn.ui.component.item.product.ProductItem
import com.spirit.kitchn.ui.component.item.product.ProductItemVO
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryScreen(
    onAddProductClicked: () -> Unit,
    onProductSwiped: (String) -> Unit,
    onItemClicked: (String) -> Unit,
    products: List<ProductItemVO>,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Pantry") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProductClicked) {
                Icon(Icons.Filled.Add, "Add Product")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProductList(
                products = products,
                onItemClick = onItemClicked,
                onProductSwiped = onProductSwiped,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Composable
private fun ProductList(
    products: List<ProductItemVO>,
    modifier: Modifier,
    onItemClick: (String) -> Unit,
    onProductSwiped: (String) -> Unit,
) {
    if (products.isEmpty()) {
        Text(
            textAlign = TextAlign.Center,
            modifier = modifier,
            text = "You have no products",
        )
    } else {
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(0.dp),
        ) {
            items(products) {
                KSwipeToDismissBox(
                    onSwipe = { onProductSwiped(it.id) }
                ) {
                    ProductItem(
                        product = it,
                        onItemClick = onItemClick,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun AddProductButton(onClick: () -> Unit) {
    KButton(
        onClick = onClick,
        modifier = Modifier
            .testTag("buttonAddProduct")
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        label = "Add Product"
    )
}

@Composable
private fun ShowAllRecipeButton(onClick: () -> Unit) {
    KButton(
        onClick = onClick,
        modifier = Modifier
            .testTag("seeAllRecipes")
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        label = "All Recipes"
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    KTheme {
        PantryScreen(
            products = listOf(
                ProductItemVO(
                    imageUrl = "123",
                    name = "321",
                    id = "",
                ),
                ProductItemVO(
                    imageUrl = "das",
                    name = "fds",
                    id = "",
                )
            ),
            onAddProductClicked = {},
            onItemClicked = {},
            onProductSwiped = {},
        )
    }
}
