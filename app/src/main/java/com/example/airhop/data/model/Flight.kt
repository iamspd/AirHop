package com.example.airhop.data.model

/**
 * Data class that represents flight information based on
 * [departureAirport] and [destinationAirport].
 */
data class Flight(
    val departureAirport: Airport,
    val destinationAirport: Airport
)
