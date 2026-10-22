// File: app/src/main/java/com/example/hudnavigation/MainActivity.kt
package com.example.hudnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.Density
import com.example.hudnavigation.ui.HUDDashboardScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Provide a fixed density for the HUD (ensures consistent size on any device)
        setContent {
            // 1.0f density = 160 dpi (baseline). Adjust if you need a different scaling.
            CompositionLocalProvider(LocalDensity provides Density(density = 1f, fontScale = 1f)) {
                HUDDashboardScreen()
            }
        }
    }
}
