// File: app/src/main/java/com/example/hudnavigation/data/MockNavigationDataSource.kt
package com.example.hudnavigation.data

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * A completely self‑contained mock data source that emits realistic‑looking navigation
 * values every second.  It implements the same contract as a real GPS / Directions API,
 * so swapping it later is a one‑line change in the ViewModel.
 */
class MockNavigationDataSource(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : NavigationRepository {

    // ----- Unit handling ---------------------------------------------------------
    private var useMph = true               // UI toggles this flag
    private val mphToKph = 1.60934f

    // ----- Mutable state flows (private) ----------------------------------------
    private val _speed = MutableStateFlow(55)                     // mph
    private val _speedLimit = MutableStateFlow(65)                // mph
    private val _currentStreet = MutableStateFlow("Main St")
    private val _nextManeuverStreet = MutableStateFlow("Maple Ave")
    private val _nextManeuverDirection = MutableStateFlow(ManeuverDirection.TURN_RIGHT)
    private val _distanceToManeuver = MutableStateFlow("350 m")
    private val _distanceToDestination = MutableStateFlow("12.3 km")
    private val _eta = MutableStateFlow("18 min")
    private val _heading = MutableStateFlow(45f)                  // degrees

    // ----- Public read‑only flows ------------------------------------------------
    override val speedFlow: Flow<Int>
        get() = _speed.map { speed ->
            if (useMph) speed else (speed * mphToKph).toInt()
        }

    override val speedLimitFlow: Flow<Int>
        get() = _speedLimit.map { limit ->
            if (useMph) limit else (limit * mphToKph).toInt()
        }

    override val currentStreetFlow: Flow<String> = _currentStreet
    override val nextManeuverStreetFlow: Flow<String> = _nextManeuverStreet
    override val nextManeuverDirectionFlow: Flow<ManeuverDirection> = _nextManeuverDirection
    override val distanceToManeuverFlow: Flow<String> = _distanceToManeuver
    override val distanceToDestinationFlow: Flow<String> = _distanceToDestination
    override val etaFlow: Flow<String> = _eta
    override val headingFlow: Flow<Float> = _heading

    // ----- Unit toggle -----------------------------------------------------------
    override fun toggleSpeedUnit() {
        useMph = !useMph
    }

    // ----- Simulated live updates ------------------------------------------------
    init {
        coroutineScope.launch {
            while (isActive) {
                delay(1_000L)

                // Random but plausible updates
                _speed.value = (50..80).random()
                _speedLimit.value = listOf(55, 60, 65, 70).random()
                _currentStreet.value = listOf("Main St", "Broadway", "Elm St").random()
                _nextManeuverStreet.value = listOf("Maple Ave", "Pine Rd", "Oak Blvd").random()
                _nextManeuverDirection.value = ManeuverDirection.values().random()
                _distanceToManeuver.value = "${(200..800).random()} m"
                _distanceToDestination.value = "${(5..20).random() + (0..9).random() / 10f} km"
                _eta.value = "${(10..30).random()} min"
                _heading.value = (0..359).random().toFloat()
            }
        }
    }
}
