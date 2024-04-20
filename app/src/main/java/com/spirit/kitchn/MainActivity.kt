package com.spirit.kitchn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.spirit.kitchn.infrastructure.navigation.NavigationGraph
import com.spirit.kitchn.ui.theme.KTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KTheme {
                NavigationGraph()
            }
        }
    }
}