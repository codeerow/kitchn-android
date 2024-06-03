package com.spirit.kitchn.ui.screen.dashboard

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
import com.spirit.kitchn.ui.component.CaloriesSection
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Dashboard") })
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CaloriesSection()
            Spacer(modifier = Modifier.weight(1f))
            AddProductButton(
                onClick = {},
            )
            Spacer(modifier = Modifier.height(16.dp))
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
        label = "Shopping list"
    )
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    KTheme {
        DashboardScreen()
    }
}