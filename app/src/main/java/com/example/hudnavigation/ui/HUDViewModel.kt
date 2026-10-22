// File: app/src/main/java/com/example/hudnavigation/ui/HUDViewModel.kt
package com.example.hudnavigation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hudnavigation.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Holds a single immutable UI state that the Compose screen consumes.
 */
data class HUDUiState(
    val speed: Int = 0,
    val speedUnit: SpeedUnit = SpeedUnit.MPH,
    val speedLimit: Int = 0,
    val currentStreet: String = "",
    val nextStreet: String = "",
    val nextDirection: ManeuverDirection = ManeuverDirection.STRAIGHT,
    val distanceToTurn: String = "",
    val distanceToDest: String = "",
    val eta: String = "",
    val heading: Float = 0f,
    val isOverSpeedLimit: Boolean = false
)

enum class SpeedUnit { MPH, KMH }

class HUDViewModel(
    // Default to the mock source – just swap the argument for a real implementation.
    private val repository: NavigationRepository = MockNavigationDataSource()
) : ViewModel() {

    // ----- Combine the many flows into ONE UI state -----------------------------
    val uiState: StateFlow<HUDUiState> = combine(
        repository.speedFlow,
        repository.speedLimitFlow,
        repository.currentStreetFlow,
        repository.nextManeuverStreetFlow,
        repository.nextManeuverDirectionFlow,
        repository.distanceToManeuverFlow,
        repository.distanceToDestinationFlow,
        repository.etaFlow,
        repository.headingFlow
    ) { speed,
        speedLimit,
        currentStreet,
        nextStreet,
        nextDirection,
        distanceToTurn,
        distanceToDest,
        eta,
        heading ->

        HUDUiState(
            speed = speed,
            speedUnit = if (speed <= 200) SpeedUnit.MPH else SpeedUnit.KMH, // simple inference; toggle will change
