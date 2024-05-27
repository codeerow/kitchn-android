package com.spirit.kitchn

import android.os.Bundle
import androidx.activity.compose.setContent
import com.spirit.kitchn.infrastructure.navigation.NavigationGraph
import com.spirit.kitchn.ui.theme.KTheme
import org.koin.androidx.scope.ScopeActivity

class MainActivity : ScopeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KTheme {
                NavigationGraph()
            }
        }
    }
}