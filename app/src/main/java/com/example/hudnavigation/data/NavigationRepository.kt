// File: app/src/main/java/com/example/hudnavigation/data/NavigationRepository.kt
package com.example.hudnavigation.data

import kotlinx.coroutines.flow.Flow

/**
 * Contract for any navigation data source (mock, live GPS, Google‑Maps, etc.).
 */
interface NavigationRepository {

    /** Current vehicle speed – the value respects the unit chosen by the UI (mph or km/h). */
    val speedFlow: Flow<Int>

    /** Speed‑limit of the road the vehicle is currently on. */
    val speedLimitFlow: Flow<Int>

    /** Name of the street we are currently driving on. */
    val currentStreetFlow: Flow<String>

    /** Name of the street for the upcoming maneuver. */
    val nextManeuverStreetFlow: Flow<String>

    /** Direction enum for the next maneuver arrow. */
    val nextManeuverDirectionFlow: Flow<ManeuverDirection>

    /** Human‑readable distance to the next maneuver (e.g. “350 m”). */
    val distanceToManeuverFlow: Flow<String>

    /** Remaining distance to the final destination (e.g. “12.3 km”). */
    val distanceToDestinationFlow: Flow<String>

    /** Estimated time of arrival (e.g. “18 min”). */
    val etaFlow: Flow<String>

    /** Compass heading in degrees, 0‑360 where 0 = North. */
    val headingFlow: Flow<Float>

    /** Allows the UI to toggle between mph / km/h. */
    fun toggleSpeedUnit()
}
