package com.spirit.kitchn.ui.component.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spirit.kitchn.R
import com.spirit.kitchn.core.recipe.model.RecipeDTO

@Composable
fun RecipeItem(
    item: RecipeDTO,
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
                if (item.images.isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.images[0].url)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
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