package com.spirit.kitchn.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.theme.KTheme

@Composable
fun KButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    textPadding: PaddingValues = PaddingValues(0.dp),
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(25f),
    ) {
        Text(
            modifier = Modifier
                .padding(textPadding),
            text = label,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CoinStarButtonPreview() {
    KTheme {
        KButton(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .fillMaxWidth(),
            onClick = {},
            label = "Button Button",
            textPadding = PaddingValues(8.dp),
        )
    }
}