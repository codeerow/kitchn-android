package com.spirit.kitchn.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.spirit.kitchn.core.user.product.model.Product
import com.spirit.kitchn.ui.theme.KTheme

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
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
                .padding(16.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
                    .padding(3.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
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
            product = Product(barcode = "das", name = "fds", imageUrl = "eq", id = ""),
            backgroundColor = Color.LightGray,
            onItemClick = {},
        )
    }
}