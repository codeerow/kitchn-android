package com.spirit.kitchn.ui.component.item.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.spirit.kitchn.ui.theme.KTheme

@Composable
fun ProductItem(
    product: ProductItemVO,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onItemClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { onItemClick(product.id) }
            .background(backgroundColor)
    ) {
        Row(
            modifier = modifier
        ) {
            SubcomposeAsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(all = 8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview
@Composable
private fun ProductItem_preview() {
    KTheme {
        ProductItem(
            product = ProductItemVO(imageUrl = "das", name = "fds", id = ""),
            onItemClick = {},
        )
    }
}