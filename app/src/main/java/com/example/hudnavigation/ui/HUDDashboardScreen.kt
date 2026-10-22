// File: app/src/main/java/com/example/hudnavigation/ui/HUDDashboardScreen.kt
package com.example.hudnavigation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HUDDashboardScreen() {
    // ------- Mock data sources (replace with real sensor / navigation data) -------
    var speedMph by remember { mutableStateOf(55f) }            // current speed
    var isMph by remember { mutableStateOf(true) }               // unit toggle
    var speedLimitMph by remember { mutableStateOf(65f) }        // speed limit
    var nextTurnDirection by remember { mutableStateOf("right") } // "left", "right", "straight"
    var nextTurnStreet by remember { mutableStateOf("Maple Ave") }
    var distanceToTurnM by remember { mutableStateOf(350) }      // metres
    var distanceToDestKm by remember { mutableStateOf(12.3) }    // km
    var etaMinutes by remember { mutableStateOf(18) }            // minutes
    var headingDegrees by remember { mutableStateOf(45f) }       // 0 = N

    // Simulate live updates (for demo only)
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            speedMph = (50..80).random().toFloat()
            distanceToTurnM = (200..800).random()
            distanceToDestKm = (5..20).random() + (0..9).random() / 10f
            etaMinutes = (10..30).random()
            headingDegrees = (0..359).random().toFloat()
        }
    }

    // ------- UI -------
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1️⃣ Current Speed
            SpeedDisplay(
                speed = speedMph,
                isMph = isMph,
                onToggleUnit = { isMph = !isMph }
            )

            // 2️⃣ Speed Limit (with warning frame if over limit)
            SpeedLimitDisplay(
                limitMph = speedLimitMph,
                currentSpeedMph = speedMph,
                isMph = isMph
            )

            // 3️⃣ Next Turn
            NextTurnDisplay(
                direction = nextTurnDirection,
                street = nextTurnStreet,
                distanceM = distanceToTurnM,
                isMph = isMph
            )

            // 4️⃣ Distance & ETA
            DestinationInfo(
                distanceKm = distanceToDestKm,
                etaMinutes = etaMinutes
            )

            // 5️⃣ Compass Heading
            CompassHeading(headingDegrees = headingDegrees)
        }
    }
}

/
