package com.spirit.kitchn.ui.component.item.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.spirit.kitchn.R
import com.spirit.kitchn.ui.theme.KTheme

@Composable
fun RecipeItem(
    item: RecipeItemVO,
    isPreview: Boolean = false,
    onItemClicked: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onItemClicked(item.id) },
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isPreview) {
                Image(
                    painterResource(id = R.drawable.imgone),
                    contentDescription = "images",
                    alignment = Alignment.Center,
                )
            } else {
                item.imageUrl?.let { preview ->
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(preview)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        loading = { CircularProgressIndicator() },
                        alignment = Alignment.Center,
                    )
                }
            }
        }
        Text(
            text = item.name,
            modifier = Modifier.padding(
                horizontal = 8.dp,
            )
        )
    }
}

@Preview
@Composable
private fun RecipeItem_preview() {
    KTheme {
        RecipeItem(
            item = RecipeItemVO(
                id = "1",
                name = "test",
                imageUrl = null,
            ),
            isPreview = true,
            onItemClicked = {},
        )
    }
}
