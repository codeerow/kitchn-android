package com.spirit.kitchn.ui.screen.cooking

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
fun ErrorScreen(
    title: String,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(title) })
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
            KButton(
                onClick = onBackClick,
                modifier = Modifier
                    .testTag("buttonAddProduct")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Back"
            )
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    KTheme {
        ErrorScreen(
            title = "Error screen",
            onBackClick = {},
        )
    }
}
